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
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.http.HTTPException;

public class ConexaoDrive {
    
    
    /*Varíaveis e constantes globais necessárias as funcionalidades de conexão
    inserção, leitura e remoção de arquivos no google drive*/
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    
    private static final List<String> SCOPES = new ArrayList(DriveScopes.all());
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static NetHttpTransport HTTP_TRANSPORT = null;
    private static Drive servico = null;

    /**Método para buscar as credenciais de acesso a conta do google drive, com ela é possível se conectar a conta
    *@author João Paulo e Leandro
    *@version 2.0
    *@param NetHttpTransport, Recebe por parametro a constante do protocolo de transporte, nesse caso HTTP
    *@return Credential, Retorna a credencial para a conexão segura com a conta do google drive
    *@throw Pode "lançar" uma exceção do tipo entrada e saída
    *Baseado nas funções do Java QuickStart Api para Google Drive @link https://developers.google.com/drive/api/v3/quickstart/java
    */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        /*busca o arquivo de credenciais da conta*/
        InputStream in = ConexaoDrive.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        /*seta esse arquivo ao google client secrets, classe para validar a credencial*/
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        /*Então solicita o código de autorização para fazer a conexão com a conta do google drive,
        passa o protocolo de transporte, o formato do arquivo de segurança, o arquivo, e o escopo de abrangencia
        da conexão*/
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)    
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        /*retorna então o codigo de autorização*/
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
    /*Construtor da classe*/
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
        /*caso não consiga, retorna uma exceção de entrada e saída*/
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    
    /**Método que cria uma instancia da conexão com o drive
    *@author João Paulo e Leandro
    *@version 1.0
    *@return Drive, Retorna uma instância do Drive service
    */
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

    /**Método para criar um diretorio dentro da pasta principal do google drive
    *@author João Paulo e Leandro
    *@param String, nome da pasta/diretório a ser criado dentro da pasta do google drive
    *@return void
    *@version 1.0
    *@throw Exceção IO, caso não consiga enviar a pasta ao drive
    *Baseado nas funções do Java QuickStart Api para Google Drive @link https://developers.google.com/drive/api/v3/quickstart/java
    */
    public static void criaPasta(String nome) {
        /*cria o arquivo*/
        File arquivo = new File();
        /*seta o nome e seu formado de dados*/
        arquivo.setName(nome);
        arquivo.setMimeType("application/vnd.google-apps.folder");
        try {
            /*e tenta chamar a função do google para criar arquivos no drive*/
            servico.files().create(arquivo).execute();
        /*caso não consiga, retorna uma exceção IO*/
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    /**Método para criar arquivo JSON dentro da pasta do google drive
    *@author João Paulo e Leandro
    *@param String, Recebe uma String com o nome que será dado ao arquivo JSON no drive
    *@param String, Recebe o camainho do arquivo que será enviado ao Drive
    *@return void
    *@version 2.0
    *@throw Exceção IO, caso não consiga enviar o arquivo ao drive
    *Baseado nas funções do Java QuickStart Api para Google Drive @link https://developers.google.com/drive/api/v3/quickstart/java
    */
    public static void criaArquivo(String nome, String caminho) {
        /*cria o arquivo*/
        File arquivo = new File();
        /*seta o nome dele*/
        arquivo.setName(nome);
        /*pega o caminho do arquivo*/
        java.io.File caminhoArquivo = new java.io.File(caminho);
        /*e pega seu conteudo*/
        FileContent conteudoArquivo = new FileContent("file/json", caminhoArquivo);
        try {
            /*e chamar a função da biblioteca do google que cria arquivo e envia para o drive*/
            servico.files().create(arquivo, conteudoArquivo)
                    /*com os campos id e nome*/
                    .setFields("id")
                    .setFields("name")
                    .execute();
        /*caso não consiga, lança uma exceção IO*/
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    
    /**Método para listar os arquivos presentes no Google Drive
    *@author João Paulo e Leandro
    *@return Lista de arquivos contidos na conta do google drive
    *@throw Exceção IO, caso não consiga buscar os arquivos
    *@version 1.0
    *Baseado nas funções do Java QuickStart Api para Google Drive @link https://developers.google.com/drive/api/v3/quickstart/java
    */
    public static List<File> listaArquivos() {
        /*cria uma lista de arquivos*/
        FileList lista;
        try {
            /*chama a função que recebe uma listagem de até 1000 arquivos do drive, com os campos id e nome*/
            lista = servico.files().list().setPageSize(1000).setFields("files(id, name)").execute();
            /*e retorna ela*/
            return lista.getFiles();
        /*caso não consiga, lança uma Exceção IO*/
        } catch (IOException ex) {
            ex.getMessage();
        }
        /*caso não consiga, retorna null*/
        return null;
    }

    /**Método que remove um arquivo do drive de acordo com o seu id
    *@author João Paulo e Leandro
    *@param String, ID do arquivo a ser removido
    *@return void
    *@throw Exceção IO, caso não consiga remover o arquivo
    *@version 1.2
    *Baseado nas funções do Java QuickStart Api para Google Drive @link https://developers.google.com/drive/api/v3/quickstart/java
    */
    public static void removerArquivo(String id) {
        /*recebe o ID do arquivo a ser deletado*/
        try {
            /*tenta deleta-lo com o método de delete da bibloteca do GDrive*/
            servico.files().delete(id).execute();
        /*caso não consiga, lança uma exceção IO*/
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    /**Método que lê os dados de um arquivo contido no GDrive
    *@author João Paulo e Leandro
    *@version 3.0
    *Baseado nas funções do Java QuickStart Api para Google Drive @link https://developers.google.com/drive/api/v3/quickstart/java
    *@param String, ID do arquivo a ser lido do Google Drive
    *@return String, retorna o texto contido no arquivo
    */
    public static String leArquivoGD(String arquivoID) throws IOException, HTTPException{
        /*cria uma array de dados de saída*/
        OutputStream outputStream = new ByteArrayOutputStream();
        /*pega o arquivo pelo seu ID e baixa os dados contidos nele*/
        servico.files().get(arquivoID)
                .executeMediaAndDownloadTo(outputStream);
        /*e retorna em formato String*/
        return outputStream.toString();
    }
    /**Método para mudar a pasta de um arquivo
    *@author João Paulo e Leandro
    *@version 1.0
    *@param String, ID do arquivo que mudará de pasta
    *@param String, ID da pasta que receberá o arquivo
    *@return void
    *@throw Exceção IO, caso não consiga trocar a pasta do arquivo
    *Baseado nas funções do Java QuickStart Api para Google Drive @link https://developers.google.com/drive/api/v3/quickstart/java
    */
    public static void mudaArquivoPasta(String arquivoID, String pastaID) {
        File file = null;
        /*recebe o arquivo com o campo que indica as pastas que estão na mesma pasta que ele*/
        try {
            file = servico.files().get(arquivoID)
                    .setFields("parents")
                    .execute();
        /*caso não consiga listar, lança uma exceção IO*/
        } catch (IOException ex) {
            ex.getMessage();
        }
        /*pega os dados desses parentes*/
        StringBuilder previousParents = new StringBuilder();
        for (String parent : file.getParents()) {
            /*vai verificando e jogando numa lista*/
            previousParents.append(parent);
            previousParents.append(',');
        }
        /*depois tenta atualizar a pasta do arquivo segundo a listagem, verificando se alguma
        das pasta tem o id igual ao passado por parametro*/
        try {
            /*atualiza a pasta do arquivo*/
            file = servico.files().update(arquivoID, null)
                    .setAddParents(pastaID)
                    .setRemoveParents(previousParents.toString())
                    .setFields("id, parents")
                    .execute();
        /*caso não consiga, lança uma exceção IO*/
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}
