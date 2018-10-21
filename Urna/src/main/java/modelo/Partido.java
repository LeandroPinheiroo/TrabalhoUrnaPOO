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
public class Partido {
    private int numero;
    private String nome;
    /**Método para pegar o número do partido
    *@author João Paulo e Leandro
    *@version 1.0
    *@return int, número do partido
    */
    public int getNumero() {
        return numero;
    }
    /**Método para 'setar' o número do partido
    *@author João Paulo e Leandro
    *@version 1.0
    *@param int, número do partido
    *@return void
    */
    public void setNumero(int numero) {
        this.numero = numero;
    }
    /**Método para pegar o nome do partido
    *@author João Paulo e Leandro
    *@version 1.0
    *@return String, número do partido
    */
    public String getNome() {
        return nome;
    }
    /**Método para 'setar' o nome do partido
    *@author João Paulo e Leandro
    *@version 1.0
    *@param String, nome do partido
    *@return void
    */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
