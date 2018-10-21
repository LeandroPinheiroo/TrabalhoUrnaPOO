/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author João Paulo e Leandro
 */
public class Eleitor {
    private String titulo_eleitor;
    private String nome;
    private String cpf;
    private Integer matriz_imagem[][];
    private int numero_urna;

    /*Construtor da classe*/
    public Eleitor() {
    }
    /**Método para pegar o titulo de eleitor
    *@author João Paulo e Leandro
    *@return String, titulo do eleitor
    *@version 1.0
    */
    public String getTitulo_eleitor() {
        return titulo_eleitor;
    }
    /**Método para 'setar' o titulo do eleitor
    *@author João Paulo e Leandro
    *@param String, titulo do eleitor
    *@return void
    *@version 1.0
    */
    public void setTitulo_eleitor(String titulo_eleitor) {
        this.titulo_eleitor = titulo_eleitor;
    }
    /**Método para pegar o nome do eleitor
    *@author João Paulo e Leandro
    *@return String, nome do eleitor
    *@version 1.0
    */
    public String getNome() {
        return nome;
    }
    /**Método para 'setar' o nome do eleitor
    *@author João Paulo e Leandro
    *@param String, nome do eleitor
    *@return void
    *@version 1.0
    */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**Método para pegar o cpf do eleitor
    *@author João Paulo e Leandro
    *@return String, cpf do eleitor
    *@version 1.0
    */
    public String getCpf() {
        return cpf;
    }
    /**Método para 'setar' o cpf do eleitor
    *@author João Paulo e Leandro
    *@param String, cpf do eleitor
    *@return void
    *@version 1.0
    */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    /**Método para pegar o imagem por uma matriz do eleitor
    *@author João Paulo e Leandro
    *@return Integer, imagem que eleitor cadastrou como senha
    *@version 1.0
    */
    public Integer[][] getMatriz_imagem() {
        return matriz_imagem;
    }
    /**Método para 'setar' a imagem por uma matriz do eleitor
    *@author João Paulo e Leandro
    *@param Integer[][], imagem que eleitor cadastrou como senha
    *@return void
    *@version 1.0
    */
    public void setMatriz_imagem(Integer[][] matriz_imagem) {
        this.matriz_imagem = matriz_imagem;
    }
}
