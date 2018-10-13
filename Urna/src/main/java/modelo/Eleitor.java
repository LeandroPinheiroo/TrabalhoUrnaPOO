/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author leandro
 */
public class Eleitor {
    private String titulo_eleitor;
    private String nome;
    private String cpf;
    private Integer matriz_imagem[][];
    private int numero_urna;


    public Eleitor() {
    }
    
    public String getTitulo_eleitor() {
        return titulo_eleitor;
    }

    public void setTitulo_eleitor(String titulo_eleitor) {
        this.titulo_eleitor = titulo_eleitor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer[][] getMatriz_imagem() {
        return matriz_imagem;
    }

    public void setMatriz_imagem(Integer[][] matriz_imagem) {
        this.matriz_imagem = matriz_imagem;
    }
}
