package conexao;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Export;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.http.HTTPException;

public class ConexaoDrive {

    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static NetHttpTransport HTTP_TRANSPORT = null;
    private static Drive servico = null;

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = ConexaoDrive.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private ConexaoDrive() {
        /*tenta estabelecer a conexão pelo protocolo http*/
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException ex) {
            ex.getMessage();
        }
        /*depois tenta estabelecer o serviço com o google drive*/
 /*determinando o nome da aplicação, a forma de conexão, no caso o protocolo acima e pega as credencials 
        da aplicação*/
        try {
            servico = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    /*cria uma instancia da conexão com o drive*/
    public static Drive getInstance() {
        /*verifica se existe alguma conexão*/
        if (servico == null) {
            /*se ela estiver nula, quer dizer que ainda não existe*/
 /*então cria uma nova*/
            new ConexaoDrive();
        }
        /*senão estiver nula, já foi criado, assim so retorna a existente*/
        return servico;
    }

    /**/
 /*função para criar um diretorio dentro da pasta principal do google drive*/
 /*recebe por parametro o nome da pasta e o serviço do google do drive*/
    public static void criaPasta(String nome) {
        File arquivo = new File();
        arquivo.setName(nome);
        arquivo.setMimeType("application/vnd.google-apps.folder");
        try {
            servico.files().create(arquivo).execute();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    /*
    *@param Recebe uma String com o nome que será dado ao arquivo JSON no drive
    *@param Recebe o camainho do arquivo que será enviado ao Drive
    *@param Recebe a instância do serviço do Drive já criada
     */
    public static void criaArquivo(String nome, String caminho) {
        File arquivo = new File();
        arquivo.setName(nome);
        java.io.File caminhoArquivo = new java.io.File(caminho);
        FileContent conteudoArquivo = new FileContent("file/json", caminhoArquivo);
        try {
            File file = servico.files().create(arquivo, conteudoArquivo)
                    .setFields("id")
                    .setFields("name")
                    .execute();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }


    /*Função para listar os arquivos presentes no Google Drive*/
 /*Recebe por parametro o servido do Drive instanciado*/
    public static List<File> listaArquivos() {
        FileList lista;
        try {
            lista = servico.files().list().setPageSize(1000).setFields("files(id, name)").execute();
            return lista.getFiles();
        } catch (IOException ex) {
            ex.getMessage();
        }
        return null;
    }

    /*Remove um arquivo do drive de acordo com o seu id*/
    public static void removerArquivo(String id) {
        try {
            servico.files().delete(id).execute();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public static String leArquivoGD(String arquivoID) throws IOException, HTTPException{
        OutputStream outputStream = new ByteArrayOutputStream();
        servico.files().get(arquivoID)
                .executeMediaAndDownloadTo(outputStream);
        return outputStream.toString();
    }
    private static Permission atualizaPermissao(String fileId, String permissionId, String newRole) {
        try {
            // First retrieve the permission from the API.
            Permission permission = servico.permissions().get(
                    fileId, permissionId).execute();
            permission.setRole(newRole);
            return servico.permissions().update(
                    fileId, permissionId, permission).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        return null;
    }

    public static void mudaArquivoPasta(String arquivoID, String pastaID) {
        File file = null;
        try {
            file = servico.files().get(arquivoID)
                    .setFields("parents")
                    .execute();
        } catch (IOException ex) {
            ex.getMessage();
        }
        StringBuilder previousParents = new StringBuilder();
        for (String parent : file.getParents()) {
            previousParents.append(parent);
            previousParents.append(',');
        }
        try {
            // Move the file to the new folder
            file = servico.files().update(arquivoID, null)
                    .setAddParents(pastaID)
                    .setRemoveParents(previousParents.toString())
                    .setFields("id, parents")
                    .execute();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}
