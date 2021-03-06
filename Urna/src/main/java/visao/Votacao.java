/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import dao.VotoDao;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Candidato;
import modelo.Eleitor;
import modelo.Partido;
import modelo.Voto;

/**
 *
 * @author João Paulo e Leandro
 */
public class Votacao extends javax.swing.JFrame {

    /**
     * Creates new form Votacao
     */
    ArrayList<Candidato> candidatos;
    ArrayList<Partido> partidos;
    Eleitor eleitor;
    Candidato candidato;
    VotoDao votoDao;
    /**Construtor do frame
     * @author João Paulo
     * @param ArrayList Candidato
     * @param Arraylist Partidos
     * @param Eleitor eleitor
     * @param VotoDao votoDao
     * @version 1.0
     */
    public Votacao(ArrayList<Candidato> candidatos, ArrayList<Partido> partidos, Eleitor eleitor, VotoDao votoDao) {
        initComponents();
        this.setTitle("Urna");
        this.candidatos = candidatos;
        this.partidos = partidos;
        this.eleitor = eleitor;
        this.votoDao = votoDao;
        setPropriedades();
    }
    /**Método para pegar os dados de um candidato de acordo com o número dele e jogar nos campos do frame
     * @author João Paulo e Leandro
     * @param int, número do partido do candidato
     * @return void
     * @version 2.0
     */
    public void setCandidadto(int numero) {
        /*varre o array de candidatos*/
        if(!this.candidatos.isEmpty()){
            for (Candidato candidato : this.candidatos) {
                if(candidato != null){//caso candidato nao seja null
                    /*verifica se algum deles tem o número igual ao do parametro*/
                    if (candidato.getNumero() == numero) {
                        /*se encontrar, seta os dados dele nos campos do frame*/
                        campoNome.setText(candidato.getNome());
                        campoPartido.setText(candidato.getPartido().getNome());
                        campoNumero.setText(String.valueOf(candidato.getNumero()));
                        /*e guarda o candidato*/
                        this.candidato = candidato;
                    }
                }
            }
        }
    }
    /**Método para pegar o número pressionado e adicionar no campo texto
     * @author João Paulo e Leandro
     * @param ActionEvent, evento de tecla
     * @return void
     * @version 2.5
     */
    public void numeroPressionado(ActionEvent e) {
        /*guarda o texto já contido no campo*/
        String temp = campoVotacao.getText();
        /*e se não tem tamanho maior igual a 2, ainda pode receber dados, para limitar aos
        dois números do partido*/
        if (temp.length() < 2) {
            /*adiciona mais dados ao campo*/
            temp += e.getActionCommand();
        }
        /*depois seta o novo valor no campo*/
        campoVotacao.setText(temp);
        /*quando estiver igual a 2, busca o candidato daquele número*/
        if (temp.length() == 2) {
            this.setCandidadto(Integer.parseInt(temp));
        }
    }
    /**Método para cadastrar o voto
     * @author João Paulo e Leandro
     * @return Voto, retorna a instancia do voto
     * @version 2.0
     */
    public Voto votacao() {
        Voto voto = new Voto();
        voto.setCandidato(this.candidato);
        return voto;
    }
    /**Método para voto em branco
     * @author João Paulo e Leandro
     * @return Voto, retorna instancia do voto
     * @version 2.0
     */
    public Voto votaBranco() {
        Voto voto = new Voto();
        voto.setCandidato(null);
        return voto;
    }
    /**Método para validar os campos do frame
     * @author João Paulo e Leandro
     * @return String, dados que não foram preenchidos
     * @version 2.0
     */
    public String validaCampos() {
        /*verifica se alguns dos campos não está vazio, indicando que o usuário não selecionou o candidato*/
        if (campoNome.getText().equals("") || campoNumero.getText().equals("") || campoPartido.getText().equals("")
                || campoVotacao.getText().equals("")) {
            /*retorna uma string mensagem*/
            return "Candidato não selecionado! Por favor selecione seu candidato";
        }
        /*senão retorna string vazia*/
        return "";
    }
    /**Método para limpar os campos
     * @author João Paulo e Leandro
     * @version 1.0
     */
    public void limparCampos() {
        /*seta todos os textfield como string vazia*/
        campoNome.setText("");
        campoNumero.setText("");
        campoPartido.setText("");
        campoVotacao.setText("");
    }
    /**Método para modificar o frame de forma que se parece mais com uma urna
     * @author João Paulo e Leandro
     * @version 1.0
     */
    public final void setPropriedades() {
        /*seta as propriedades de forma que os botões de confirma, branco e 
        corrige se parecem mais com uma urna*/
        botaoBranco.setContentAreaFilled(false);
        botaoBranco.setOpaque(true);
        botaoBranco.setBackground(Color.WHITE);
        botaoCorrige.setContentAreaFilled(false);
        botaoCorrige.setOpaque(true);
        botaoCorrige.setBackground(Color.ORANGE);
        botaoConfirma.setContentAreaFilled(false);
        botaoConfirma.setOpaque(true);
        botaoConfirma.setBackground(Color.GREEN);
        botaoConfirma.requestFocus();
        campoPresidente.setHorizontalAlignment(JTextField.CENTER);
        campoNumero.setHorizontalAlignment(JTextField.CENTER);
        campoPartido.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        painelBotoes = new javax.swing.JPanel();
        botao1 = new javax.swing.JButton();
        botao2 = new javax.swing.JButton();
        botao3 = new javax.swing.JButton();
        botao4 = new javax.swing.JButton();
        botao5 = new javax.swing.JButton();
        botao6 = new javax.swing.JButton();
        botao7 = new javax.swing.JButton();
        botao8 = new javax.swing.JButton();
        botao9 = new javax.swing.JButton();
        botao0 = new javax.swing.JButton();
        botaoConfirma = new javax.swing.JButton();
        botaoCorrige = new javax.swing.JButton();
        botaoBranco = new javax.swing.JButton();
        painelVisual = new javax.swing.JPanel();
        campoPresidente = new javax.swing.JTextField();
        campoNumero = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        campoPartido = new javax.swing.JTextField();
        campoVotacao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        jButton3.setText("2");
        jButton3.setMaximumSize(new java.awt.Dimension(100, 60));
        jButton3.setMinimumSize(new java.awt.Dimension(25, 15));
        jButton3.setPreferredSize(new java.awt.Dimension(50, 30));

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Votação");
        setMinimumSize(new java.awt.Dimension(700, 400));
        setName("telaVotacao"); // NOI18N

        painelBotoes.setBackground(new java.awt.Color(57, 47, 47));
        painelBotoes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        painelBotoes.setMaximumSize(new java.awt.Dimension(285, 378));
        painelBotoes.setMinimumSize(new java.awt.Dimension(280, 378));
        painelBotoes.setName(""); // NOI18N
        painelBotoes.setPreferredSize(new java.awt.Dimension(280, 378));

        botao1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao1.setText("1");
        botao1.setMaximumSize(new java.awt.Dimension(100, 60));
        botao1.setMinimumSize(new java.awt.Dimension(25, 15));
        botao1.setPreferredSize(new java.awt.Dimension(50, 30));
        botao1.setRequestFocusEnabled(false);
        botao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao1ActionPerformed(evt);
            }
        });

        botao2.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao2.setText("2");
        botao2.setMaximumSize(new java.awt.Dimension(100, 60));
        botao2.setMinimumSize(new java.awt.Dimension(25, 15));
        botao2.setPreferredSize(new java.awt.Dimension(50, 30));
        botao2.setRequestFocusEnabled(false);
        botao2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao2ActionPerformed(evt);
            }
        });

        botao3.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao3.setText("3");
        botao3.setMaximumSize(new java.awt.Dimension(100, 60));
        botao3.setMinimumSize(new java.awt.Dimension(25, 15));
        botao3.setPreferredSize(new java.awt.Dimension(50, 30));
        botao3.setRequestFocusEnabled(false);
        botao3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao3ActionPerformed(evt);
            }
        });

        botao4.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao4.setText("4");
        botao4.setMaximumSize(new java.awt.Dimension(100, 60));
        botao4.setMinimumSize(new java.awt.Dimension(25, 15));
        botao4.setPreferredSize(new java.awt.Dimension(50, 30));
        botao4.setRequestFocusEnabled(false);
        botao4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao4ActionPerformed(evt);
            }
        });

        botao5.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao5.setText("5");
        botao5.setMaximumSize(new java.awt.Dimension(100, 60));
        botao5.setMinimumSize(new java.awt.Dimension(25, 15));
        botao5.setPreferredSize(new java.awt.Dimension(50, 30));
        botao5.setRequestFocusEnabled(false);
        botao5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao5ActionPerformed(evt);
            }
        });

        botao6.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao6.setText("6");
        botao6.setMaximumSize(new java.awt.Dimension(100, 60));
        botao6.setMinimumSize(new java.awt.Dimension(25, 15));
        botao6.setPreferredSize(new java.awt.Dimension(50, 30));
        botao6.setRequestFocusEnabled(false);
        botao6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao6ActionPerformed(evt);
            }
        });

        botao7.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao7.setText("7");
        botao7.setMaximumSize(new java.awt.Dimension(100, 60));
        botao7.setMinimumSize(new java.awt.Dimension(25, 15));
        botao7.setPreferredSize(new java.awt.Dimension(50, 30));
        botao7.setRequestFocusEnabled(false);
        botao7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao7ActionPerformed(evt);
            }
        });

        botao8.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao8.setText("8");
        botao8.setMaximumSize(new java.awt.Dimension(100, 60));
        botao8.setMinimumSize(new java.awt.Dimension(25, 15));
        botao8.setPreferredSize(new java.awt.Dimension(50, 30));
        botao8.setRequestFocusEnabled(false);
        botao8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao8ActionPerformed(evt);
            }
        });

        botao9.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao9.setText("9");
        botao9.setMaximumSize(new java.awt.Dimension(100, 60));
        botao9.setMinimumSize(new java.awt.Dimension(25, 15));
        botao9.setPreferredSize(new java.awt.Dimension(50, 30));
        botao9.setRequestFocusEnabled(false);
        botao9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao9ActionPerformed(evt);
            }
        });

        botao0.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botao0.setText("0");
        botao0.setMaximumSize(new java.awt.Dimension(100, 60));
        botao0.setMinimumSize(new java.awt.Dimension(25, 15));
        botao0.setPreferredSize(new java.awt.Dimension(50, 30));
        botao0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao0ActionPerformed(evt);
            }
        });

        botaoConfirma.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botaoConfirma.setText("Confirma");
        botaoConfirma.setRequestFocusEnabled(false);
        botaoConfirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmaActionPerformed(evt);
            }
        });

        botaoCorrige.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botaoCorrige.setText("Corrige");
        botaoCorrige.setRequestFocusEnabled(false);
        botaoCorrige.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCorrigeActionPerformed(evt);
            }
        });

        botaoBranco.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        botaoBranco.setText("Branco");
        botaoBranco.setRequestFocusEnabled(false);
        botaoBranco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBrancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelBotoesLayout = new javax.swing.GroupLayout(painelBotoes);
        painelBotoes.setLayout(painelBotoesLayout);
        painelBotoesLayout.setHorizontalGroup(
            painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotoesLayout.createSequentialGroup()
                .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelBotoesLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botao3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelBotoesLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(painelBotoesLayout.createSequentialGroup()
                                .addComponent(botao4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(botao5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelBotoesLayout.createSequentialGroup()
                                .addComponent(botao7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(botao8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(botao0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botao9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botao6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelBotoesLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(botaoBranco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoCorrige)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoConfirma)
                .addGap(30, 30, 30))
        );
        painelBotoesLayout.setVerticalGroup(
            painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotoesLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(botao0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoBranco)
                    .addComponent(botaoCorrige)
                    .addComponent(botaoConfirma))
                .addGap(58, 58, 58))
        );

        painelVisual.setBackground(new java.awt.Color(250, 243, 243));
        painelVisual.setRequestFocusEnabled(false);

        campoPresidente.setEditable(false);
        campoPresidente.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        campoPresidente.setText("Presidente");

        campoNumero.setEditable(false);
        campoNumero.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        campoNumero.setRequestFocusEnabled(false);

        jLabel1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel1.setText("Número:");

        jLabel2.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel2.setText("Nome:");

        campoNome.setEditable(false);
        campoNome.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        campoNome.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel3.setText("Partido:");

        campoPartido.setEditable(false);
        campoPartido.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        campoPartido.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        campoVotacao.setEditable(false);
        campoVotacao.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        campoVotacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setText("Número Digitado:");

        javax.swing.GroupLayout painelVisualLayout = new javax.swing.GroupLayout(painelVisual);
        painelVisual.setLayout(painelVisualLayout);
        painelVisualLayout.setHorizontalGroup(
            painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVisualLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelVisualLayout.createSequentialGroup()
                        .addGroup(painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelVisualLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoNumero))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelVisualLayout.createSequentialGroup()
                                .addGroup(painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelVisualLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelVisualLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(6, 6, 6)))
                                .addGroup(painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campoNome)
                                    .addComponent(campoPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(campoPresidente, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoVotacao, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelVisualLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(126, 126, 126))))
        );
        painelVisualLayout.setVerticalGroup(
            painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelVisualLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoVotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(campoPresidente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelVisualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(campoPartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelVisual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelVisual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addComponent(painelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao1ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao1ActionPerformed

    private void botao2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao2ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao2ActionPerformed

    private void botao3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao3ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao3ActionPerformed

    private void botao4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao4ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao4ActionPerformed

    private void botao5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao5ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao5ActionPerformed

    private void botao6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao6ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao6ActionPerformed

    private void botao7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao7ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao7ActionPerformed

    private void botao8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao8ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao8ActionPerformed

    private void botao9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao9ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao9ActionPerformed

    private void botao0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao0ActionPerformed
        numeroPressionado(evt);
    }//GEN-LAST:event_botao0ActionPerformed

    private void botaoBrancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBrancoActionPerformed
        Voto v = votaBranco();
        votoDao.cadastraVoto(v);
        String caminho = "somurna.wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(caminho).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Voto em branco cadastrado com sucesso", "Voto em BRANCO", JOptionPane.INFORMATION_MESSAGE);
        this.limparCampos();
        this.dispose();
    }//GEN-LAST:event_botaoBrancoActionPerformed

    private void botaoCorrigeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCorrigeActionPerformed
        this.limparCampos();
    }//GEN-LAST:event_botaoCorrigeActionPerformed

    private void botaoConfirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmaActionPerformed
        String erro = validaCampos();
        if (!erro.equals("")) {
            JOptionPane.showMessageDialog(this, erro, "Erro ao cadastrar Eleitor", JOptionPane.ERROR_MESSAGE);
            limparCampos();
            return;
        }
        Voto v = votacao();
        votoDao.cadastraVoto(v);
        InputStream musica;
        String caminho = "somurna.wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(caminho).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Voto cadastrado com sucesso", "Cadastramento de Voto", JOptionPane.INFORMATION_MESSAGE);
        this.limparCampos();
        this.dispose();
    }//GEN-LAST:event_botaoConfirmaActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botao0;
    private javax.swing.JButton botao1;
    private javax.swing.JButton botao2;
    private javax.swing.JButton botao3;
    private javax.swing.JButton botao4;
    private javax.swing.JButton botao5;
    private javax.swing.JButton botao6;
    private javax.swing.JButton botao7;
    private javax.swing.JButton botao8;
    private javax.swing.JButton botao9;
    private javax.swing.JButton botaoBranco;
    private javax.swing.JButton botaoConfirma;
    private javax.swing.JButton botaoCorrige;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoNumero;
    private javax.swing.JTextField campoPartido;
    private javax.swing.JTextField campoPresidente;
    private javax.swing.JTextField campoVotacao;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelVisual;
    // End of variables declaration//GEN-END:variables
}
