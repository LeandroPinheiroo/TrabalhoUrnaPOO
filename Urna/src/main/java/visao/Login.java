/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import conexao.ConexaoDrive;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.ws.http.HTTPException;
import uteis.Arquivo;
import com.google.gson.Gson;
import dao.VotoDao;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JOptionPane;
import modelo.Candidato;
import modelo.Eleitor;
import modelo.Partido;

/**
 *
 * @author João Paulo e Leandro
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Logine
     */
    ArrayList<Candidato> candidatos;
    ArrayList<Partido> partidos;
    Eleitor eleitor = null;
    VotoDao votoDao;
    /**Construtor do Frame
     *@param VotoDao, instancia do Dao da classe de votos
     *@param Integer, verificação se vai atualizar dados ou não
     *@version 4.0
     */
    public Login(VotoDao votoDao, Integer atualizar) {
        /*cria a primeira thread, para carregar os componentes do frame*/
        Thread t1 = new Thread() {
            public void run() {
                /*cria os componentes*/
                initComponents();
                /*deixa a barra de progresso invisivel*/
                barraProgresso.setVisible(false);
                /*coloca a localização do frame no meio*/
                setLocationRelativeTo(null);
                /*coloca o titulo do frame*/
                setTitle("Login");
                /*coloca o campo do CPF como editável*/
                campoCpf.setEditable(true);
            }
        };
        t1.start();
        /*da um join na thread, para obrigar que ele tenha sua execução terminada*/
        try {
            t1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*se for para atualizar os dados*/
        if (atualizar == JOptionPane.YES_OPTION) {
            /*coloca a barra de progresso vísivel*/
            barraProgresso.setVisible(true);
            /*desabilita o botão de conectar para obrigar o usuário a esperar os arquivos carregarem*/
            botaoEntrar.setEnabled(false);
            /*coloca o valor da barra de progresso como 0, indicando que vai começar a carregar o arquivo*/
            barraProgresso.setValue(0);
            /*cria a nova thread*/
            Thread t = new Thread() {
                @Override
                /*essa thread vai carregar os primeiros dois arquivos*/
                public void run() {
                    try {
                        barraProgresso.setValue(10);
                        sleep(500);
                        barraProgresso.setValue(20);
                        criaArquivoCandidatos();
                        candidatos = geraObjetoCandidato();
                        barraProgresso.setValue(50);
                        criaArquivoPartidos();
                        partidos = geraObjetoPartido();
                        barraProgresso.setValue(70);
                    } catch (InterruptedException e) {
                        Logger.getLogger("Erro");
                    }
                }
            };
            t.start();
            /*já a ultima thread, vai carregar o arquivo mais pesado, então ela vai por ultimo*/
            Thread t2 = new Thread() {
                public void run() {
                    /*chama o método de baixar o arquivo de eleitores*/
                    criaArquivoEleitores();
                    /*barra de progresso vai para 100, para demonstrar que os arquivos já foram carregados*/
                    barraProgresso.setValue(100);
                    /*permite a conexão no login, liberado o campo CPF e o botão de conectar*/
                    campoCpf.setEditable(true);
                    botaoEntrar.setEnabled(true);
                    try {
                        /*depois da um tempo de 1 segundo, antes de fazer a barra de progresso sumir*/
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    barraProgresso.setVisible(false);
                }
            };
            t2.start();

        }
        this.votoDao = votoDao;
    }
    /**Método para baixar os dados do arquivo de eleitores do GDrive, e criar um arquivo local com esses dados
     * @author João Paulo e  Leandro
     * @version 4.5
     */
    public void criaArquivoEleitores() {
        /*inicia a conexão com o GDrive*/
        ConexaoDrive.getInstance();
        /*pega a listagem de arquivos contidos lá*/
        List<com.google.api.services.drive.model.File> lista_arquivos = ConexaoDrive.listaArquivos();
        /*varre a lista de arquivos*/
        for (com.google.api.services.drive.model.File lista_arquivo : lista_arquivos) {
            /*para verificar o qual arquivo tem o mesmo nome que o de eleitores*/
            if (lista_arquivo.getName().equals("eleitores.json")) {
                try {
                    /*pega o conteudo do arquivo*/
                    String conteudo = ConexaoDrive.leArquivoGD(lista_arquivo.getId());
                    /*e salva em outro arquivo local*/
                    Arquivo.criaArquivo(conteudo, "eleitores.json");
                    /*retorna so para parar a execução*/
                    return;
                /*caso não consiga, lança exceção IO ou exceção HTTP*/
                } catch (IOException | HTTPException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
        }
    }
    /**Método para baixar os dados do arquivo de candidatos e coloca-los em um arquivo local
     * @author João Paulo e Leandro
     * @version 4.5
     */
    public void criaArquivoCandidatos() {
        /*inicia a conexão com o GDrive*/
        ConexaoDrive.getInstance();
        /*pega a listagem de arquivos contidos lá*/
        List<com.google.api.services.drive.model.File> lista_arquivos = ConexaoDrive.listaArquivos();
        /*varre a lista de arquivos*/
        for (com.google.api.services.drive.model.File lista_arquivo : lista_arquivos) {
            /*para verificar o qual arquivo tem o mesmo nome que o de candidatos*/
            if (lista_arquivo.getName().equals("candidatos.json")) {
                try {
                    /*pega o conteudo do arquivo*/
                    String conteudo = ConexaoDrive.leArquivoGD(lista_arquivo.getId());
                    /*e salva em outro arquivo local*/
                    Arquivo.criaArquivo(conteudo, "candidatos.json");
                    /*retorna so para parar a execução*/
                    return;
                /*caso não consiga, lança exceção IO ou exceção HTTP*/
                } catch (IOException | HTTPException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
        }
    }
    /**Método para baixar os dados do arquivo de partidos do GDrive e coloca-los em um arquivo local
     * @author João Paulo e Leandro
     * @version 4.5
     */
    public void criaArquivoPartidos() {
        /*inicia a conexão com o GDrive*/
        ConexaoDrive.getInstance();
        /*pega a listagem de arquivos contidos lá*/
        List<com.google.api.services.drive.model.File> lista_arquivos = ConexaoDrive.listaArquivos();
        /*varre a lista de arquivos*/
        for (com.google.api.services.drive.model.File lista_arquivo : lista_arquivos) {
            /*para verificar o qual arquivo tem o mesmo nome que o de partidos*/
            if (lista_arquivo.getName().equals("partidos.json")) {
                try {
                    /*pega o conteudo do arquivo*/
                    String conteudo = ConexaoDrive.leArquivoGD(lista_arquivo.getId());
                    /*e salva em outro arquivo local*/
                    Arquivo.criaArquivo(conteudo, "partidos.json");
                    /*retorna so para parar a execução*/
                    return;
                /*caso não consiga, lança exceção IO ou exceção HTTP*/
                } catch (IOException | HTTPException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
        }
    }
    
    /**Método para comparar os dados da matriz imagem recebida no login, e a cadastrada no eleitor
     e verificar se são realmente iguais
     * @author João Paulo e Leandro
     * @param Integer[][], matriz imagem que o usuário cadastrou como senha
     * @param Eleitor, instancia do Eleitor
     * @return Boolean, retorna se as imagens são iguais ou não
     * @version 2.3
     */
    public boolean comparaMatriz(Integer matriz[][], Eleitor eleitor) {
        /*pega as linhas e colunas da matriz*/
        int linhas;
        int colunas;
        linhas = matriz.length;
        colunas = matriz[0].length;
        /*varre a matriz entre linhas e colunas*/
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                /*se alguma parte da matriz imagem, for diferente da outra, já retorna erro e mostra mensagem*/
                if (!Objects.equals(matriz[i][j], eleitor.getMatriz_imagem()[i][j])) {
                    JOptionPane.showMessageDialog(this, "Imagem de acesso invalida", "Erro ao entrar", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        /*senão retorna verdadeiro*/
        return true;
    }
    /**Método para validar se os campos estão preenchidos corretamente
     * @author João Paulo e Leandro
     * @version 1.0
     * @return boolean, retorna se os campos estão válidos ou não
     */
    public boolean validaCampos() {
        /*Método simples, verifica se os campos não estão com dados preenchidos, e retorna false se não estiverem
        e mostra mensagem de erro*/
        if (campoCpf.getText().equals("   .   .   -  ")) {
            JOptionPane.showMessageDialog(this, "Entre com o CPF para efetuar login", "Erro ao entrar", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (campoArquivo.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Entre com a imagem de acesso para efetuar login", "Erro ao entrar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        /*se estiver tudo ok, retorna verdadeiro*/
        return true;
    }
    /**Método para validar o login do eleitor, e permiter a conexão com o sistema
     * @author João Paulo e Leandro
     * @param Integer[][], matriz imagem que o usuário passou no campo senha
     * @param String, CPF que o eleitor passaou no campo CPF
     * @param ArrayList Eleitor, arraylist de eleitores cadastrados
     * @return boolean, retorna se o login é valido ou não
     * @version 3.5
     */
    public boolean validaLogin(Integer matriz[][], String cpf, ArrayList<Eleitor> eleitores) {
        /*valida os campos primeiro*/
        if (validaCampos() == false) {
            return false;
        }
        /*depois varre a lista de eleitores*/
        for (Eleitor eleitor : eleitores) {
            /*e verifica um eleitor que bate com o cpf recebido*/
            if (eleitor.getCpf().equals(cpf)) {
                /*então compara se a imagem é a mesma*/
                if (comparaMatriz(matriz, eleitor)) {
                    /*se for retorna true*/
                    this.eleitor = eleitor;
                    return true;
                } else {
                    /*senão retorna false*/
                    return false;
                }
            }
        }
        /*e mostra mensagem de erro*/
        JOptionPane.showMessageDialog(this, "CPF não cadastrado", "Erro ao entrar", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    /**Método para pegar o arquivo de Partidos, e jogar os dados em um arraylist
     * @author João Paulo e Leandro
     * @return ArrayList Partido, retorna o arraylist de partidos
     * @version 2.0
     */
    public ArrayList<Partido> geraObjetoPartido() {
        /*instancia a classe para gerar arquivo JSON*/
        Gson gson = new Gson();
        FileInputStream arquivoEntrada;
        ArrayList<Partido> partidos = null;
        try {
            /*pega o arquivo*/
            arquivoEntrada = new FileInputStream("partidos.json");
            /*lê os dados do arquivo*/
            BufferedReader leitor = new BufferedReader(new InputStreamReader(arquivoEntrada));
            partidos = new ArrayList();
            String strLine;
            /*e vai escrevendo linha por linha, cada linha, um indice no arraylist*/
            while ((strLine = leitor.readLine()) != null) {
                partidos.add(gson.fromJson(strLine, Partido.class));
            }
            /*depois fecha o arquivo*/
            leitor.close();
        /*caso não consiga, lança exceções de FileNotFound e IO*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*e no fim retorna o arraylist de partidos, vazio ou preenchido depende se conseguiu ou não*/
        return partidos;
    }
      /**Método para pegar o arquivo de Candidatos, e jogar os dados em um arraylist
     * @author João Paulo e Leandro
     * @return ArrayList Partido, retorna o arraylist de candidatos
     * @version 2.0
     */
    public ArrayList<Candidato> geraObjetoCandidato() {
        /*instancia a classe para gerar arquivo JSON*/
        Gson gson = new Gson();
        FileInputStream arquivoEntrada;
        ArrayList<Candidato> candidatos = null;
        try {
            /*pega o arquivo*/
            arquivoEntrada = new FileInputStream("candidatos.json");
            /*lê os dados do arquivo*/
            BufferedReader leitor = new BufferedReader(new InputStreamReader(arquivoEntrada));
            candidatos = new ArrayList();
            String strLine;
            /*e vai escrevendo linha por linha, cada linha, um indice no arraylist*/
            while ((strLine = leitor.readLine()) != null) {
                candidatos.add(gson.fromJson(strLine, Candidato.class));
            }
            /*depois fecha o arquivo*/
            leitor.close();
          /*caso não consiga, lança exceções de FileNotFound e IO*/
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro, arquivo não localizado!");
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*e no fim retorna o arraylist de partidos, vazio ou preenchido depende se conseguiu ou não*/
        return candidatos;
    }
    /**Método para ajustar os dados dos leitores cadastrados
     * @author João Paulo e Leandro
     * @return Boolean, retorna se deu tudo certo ou não
     * @version 2.0
     */
    public Boolean verificaLogin() {
        /*lê a matriz imagem do campo de imagem*/
        Integer imagem[][] = Arquivo.leImagemMatriz(campoArquivo.getText());
        Gson gson = new Gson();
        FileInputStream arquivoEntrada;
        try {
            /*abre o arquivo de eleitores*/
            arquivoEntrada = new FileInputStream("eleitores.json");
            /*lê os dados do arquivo*/
            BufferedReader leitor = new BufferedReader(new InputStreamReader(arquivoEntrada));
            ArrayList<Eleitor> eleitores = new ArrayList();
            String strLine;
            /*coloca cada linha do arquivo em cada indice do arraylist*/
            while ((strLine = leitor.readLine()) != null) {
                /*adiciona no arraylist*/
                eleitores.add(gson.fromJson(strLine, Eleitor.class));
            }
            /*fecha o arquivo*/
            leitor.close();
            /*depois valida o login com a função acima*/
            if (validaLogin(imagem, campoCpf.getText(), eleitores)) {
                /*e caso seja valido, mostra mensagem de sucesso e retorna true*/
                JOptionPane.showMessageDialog(this, "Usuario Logado com sucesso", "Entrou", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        /*caso não consiga, lança exceções FileNotFound e IO*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*retorna mensagem de erro, caso falhe em conectar*/
        JOptionPane.showMessageDialog(this, "Falha ao conectar no sistema!!", "Erro", JOptionPane.ERROR_MESSAGE);
        /*e retorna false*/
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campoCpf = new javax.swing.JFormattedTextField();
        jLabelCpf = new javax.swing.JLabel();
        LabelUrna1 = new javax.swing.JLabel();
        botaoArquivo = new javax.swing.JButton();
        campoArquivo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        botaoEntrar = new javax.swing.JButton();
        barraProgresso = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        campoCpf.setEditable(false);
        try {
            campoCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabelCpf.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabelCpf.setText("CPF:");

        LabelUrna1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        LabelUrna1.setText("Imagem:");

        botaoArquivo.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botaoArquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/arquivo2.png"))); // NOI18N
        botaoArquivo.setText("Escolher Arquivo");
        botaoArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoArquivoActionPerformed(evt);
            }
        });

        campoArquivo.setEditable(false);

        jLabel1.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login");

        botaoEntrar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        botaoEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/entrar.png"))); // NOI18N
        botaoEntrar.setText("Conectar");
        botaoEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEntrarActionPerformed(evt);
            }
        });

        barraProgresso.setStringPainted(true);
        barraProgresso.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(LabelUrna1)
                        .addGap(3, 3, 3)
                        .addComponent(botaoArquivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoArquivo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelCpf)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoCpf)))
                .addGap(26, 26, 26))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botaoEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(barraProgresso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCpf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelUrna1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoArquivo)
                    .addComponent(campoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(barraProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(botaoEntrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoArquivoActionPerformed
        JFileChooser arquivo = new JFileChooser();
        arquivo.setFileFilter(new FileNameExtensionFilter("Image files", "ppm"));
        arquivo.setAcceptAllFileFilterUsed(false);
        arquivo.showOpenDialog(null);
        File file = arquivo.getSelectedFile();
        String caminho = file.getAbsolutePath();
        campoArquivo.setText(caminho);
    }//GEN-LAST:event_botaoArquivoActionPerformed

    private void botaoEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEntrarActionPerformed
        if (verificaLogin()) {
            new Votacao(this.candidatos, this.partidos, eleitor, votoDao).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_botaoEntrarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelUrna1;
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton botaoArquivo;
    private javax.swing.JButton botaoEntrar;
    private javax.swing.JTextField campoArquivo;
    private javax.swing.JFormattedTextField campoCpf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelCpf;
    // End of variables declaration//GEN-END:variables
}
