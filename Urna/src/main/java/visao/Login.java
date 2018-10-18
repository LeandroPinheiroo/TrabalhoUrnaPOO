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
 * @author weth
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Logine
     */
    ArrayList<Candidato> candidatos;
    ArrayList<Partido> partidos;
    Eleitor eleitor = null;
    VotoDao votoDao;

    public Login(VotoDao votoDao, Integer atualizar) {
        Thread t1 = new Thread() {
            public void run() {
                initComponents();
                barraProgresso.setVisible(false);
                setLocationRelativeTo(null);
                setTitle("Login");
                campoCpf.setEditable(true);
            }
        };
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (atualizar == JOptionPane.YES_OPTION) {
            barraProgresso.setVisible(true);
            botaoEntrar.setEnabled(false);
            barraProgresso.setValue(0);
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        barraProgresso.setValue(10);
                        sleep(500);
                        barraProgresso.setValue(20);
                        criaArquivoCandidatos();
                        barraProgresso.setValue(45);
                        criaArquivoPartidos();
                        barraProgresso.setValue(60);
                    } catch (InterruptedException e) {
                        Logger.getLogger("Erro");
                    }
                    
                }
            };
            t.start();
            new Thread() {
                public void run() {
                    criaArquivoEleitores();
                    barraProgresso.setValue(100);
                    campoCpf.setEditable(true);
                    botaoEntrar.setEnabled(true);
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    barraProgresso.setVisible(false);
                }
            }.start(); 
        }
        this.candidatos = geraObjetoCandidato();
        this.partidos = geraObjetoPartido();
        this.votoDao = votoDao;
    }

    public void criaArquivoEleitores() {
        ConexaoDrive.getInstance();
        List<com.google.api.services.drive.model.File> lista_arquivos = ConexaoDrive.listaArquivos();
        for (com.google.api.services.drive.model.File lista_arquivo : lista_arquivos) {
            if (lista_arquivo.getName().equals("eleitores.json")) {
                try {
                    String conteudo = ConexaoDrive.leArquivoGD(lista_arquivo.getId());
                    Arquivo.criaArquivo(conteudo, "eleitores.json");
                    return;
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (HTTPException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
        }
    }

    public void criaArquivoCandidatos() {
        ConexaoDrive.getInstance();
        List<com.google.api.services.drive.model.File> lista_arquivos = ConexaoDrive.listaArquivos();
        for (com.google.api.services.drive.model.File lista_arquivo : lista_arquivos) {
            if (lista_arquivo.getName().equals("candidatos.json")) {
                try {
                    String conteudo = ConexaoDrive.leArquivoGD(lista_arquivo.getId());
                    Arquivo.criaArquivo(conteudo, "candidatos.json");

                    return;
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (HTTPException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
        }
    }

    public void criaArquivoPartidos() {
        ConexaoDrive.getInstance();
        List<com.google.api.services.drive.model.File> lista_arquivos = ConexaoDrive.listaArquivos();
        for (com.google.api.services.drive.model.File lista_arquivo : lista_arquivos) {
            if (lista_arquivo.getName().equals("partidos.json")) {
                try {
                    String conteudo = ConexaoDrive.leArquivoGD(lista_arquivo.getId());
                    Arquivo.criaArquivo(conteudo, "partidos.json");
                    return;
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (HTTPException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
        }
    }

    public boolean comparaMatriz(Integer matriz[][], Eleitor eleitor) {
        int linhas;
        int colunas;
        linhas = matriz.length;
        colunas = matriz[0].length;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (!Objects.equals(matriz[i][j], eleitor.getMatriz_imagem()[i][j])) {
                    JOptionPane.showMessageDialog(this, "Imagem de acesso invalida", "Erro ao entrar", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validaCampos() {
        if (campoCpf.getText().equals("   .   .   -  ")) {
            JOptionPane.showMessageDialog(this, "Entre com o CPF para efetuar login", "Erro ao entrar", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (campoArquivo.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Entre com a imagem de acesso para efetuar login", "Erro ao entrar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean validaLogin(Integer matriz[][], String cpf, ArrayList<Eleitor> eleitores) {
        if (validaCampos() == false) {
            return false;
        }
        for (Eleitor eleitor : eleitores) {
            if (eleitor.getCpf().equals(cpf)) {
                if (comparaMatriz(matriz, eleitor)) {
                    this.eleitor = eleitor;
                    return true;
                } else {
                    return false;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "CPF não cadastrado", "Erro ao entrar", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public ArrayList<Partido> geraObjetoPartido() {
        Gson gson = new Gson();
        FileInputStream arquivoEntrada;
        ArrayList<Partido> partidos = null;
        try {
            arquivoEntrada = new FileInputStream("partidos.json");
            BufferedReader leitor = new BufferedReader(new InputStreamReader(arquivoEntrada));
            partidos = new ArrayList();
            String strLine;
            while ((strLine = leitor.readLine()) != null) {
                partidos.add(gson.fromJson(strLine, Partido.class));
            }
            leitor.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partidos;
    }

    public ArrayList<Candidato> geraObjetoCandidato() {
        Gson gson = new Gson();
        FileInputStream arquivoEntrada;
        ArrayList<Candidato> candidatos = null;
        try {
            arquivoEntrada = new FileInputStream("candidatos.json");
            BufferedReader leitor = new BufferedReader(new InputStreamReader(arquivoEntrada));
            candidatos = new ArrayList();
            String strLine;
            while ((strLine = leitor.readLine()) != null) {
                candidatos.add(gson.fromJson(strLine, Candidato.class));
            }
            leitor.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro, arquivo não localizado!");
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return candidatos;
    }

    public Boolean verificaLogin() {
        Integer imagem[][] = Arquivo.leImagemMatriz(campoArquivo.getText());
        Gson gson = new Gson();
        FileInputStream arquivoEntrada;
        try {
            arquivoEntrada = new FileInputStream("eleitores.json");
            BufferedReader leitor = new BufferedReader(new InputStreamReader(arquivoEntrada));
            ArrayList<Eleitor> eleitores = new ArrayList();
            String strLine;
            while ((strLine = leitor.readLine()) != null) {
                eleitores.add(gson.fromJson(strLine, Eleitor.class));
            }
            leitor.close();
            if (validaLogin(imagem, campoCpf.getText(), eleitores)) {
                JOptionPane.showMessageDialog(this, "Usuario Logado com sucesso", "Entrou", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(this, "Falha ao conectar no sistema!!", "Erro", JOptionPane.ERROR_MESSAGE);
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
