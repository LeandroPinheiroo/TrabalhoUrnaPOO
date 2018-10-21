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
public class Candidato {
    private String nome;
    private int numero;
    private String cpf;
    private Partido partido;
    /**Método para pegar o nome do candidato
    *@author João Paulo e Leandro
    *@return String, nome do candidato
    *@version 1.0*/
    public String getNome() {
        return nome;
    }
    /**Método para 'setar' o nome do candidato
    *@author João Paulo e Leandro
    *@param String, nome do candidato
    *@return void
    *@version 1.0*/
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**Método para pegar o número do partido do candidato
    *@author João Paulo e Leandro
    *@return int, número do candidato
    *@version 1.0*/
    public int getNumero() {
        return numero;
    }
    /**Método para 'setar' o número do candidato
    *@author João Paulo e Leandro
    *@param int, número do candidato
    *@return void
    *@version 1.0*/
    public void setNumero(int numero) {
        this.numero = numero;
    }
    /**Método para pegar o CPF do candidato
    *@author João Paulo e Leandro
    *@return String, CPF do candidato
    *@version 1.0*/
    public String getCpf() {
        return cpf;
    }
    /**Método para 'setar' o CPF do candidato
    *@author João Paulo e Leandro
    *@param String, CPF do candidato
    *@return void
    *@version 1.0
    */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    /**Método para pegar o partido do candidato
    *@author João Paulo e Leandro
    *@return Partido, Classe partido do candidato
    *@version 1.0*/
    public Partido getPartido() {
        return partido;
    }
    /**Método para 'setar' o partido do candidato
    *@author João Paulo e Leandro
    *@param Partido, partido do candidato
    *@return void
    *@version 1.0
    */
    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}
