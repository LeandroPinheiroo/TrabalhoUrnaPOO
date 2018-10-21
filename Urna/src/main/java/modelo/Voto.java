/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author weth
 */
public class Voto {
    private Candidato candidato;
    /**Método para pegar o candidato do voto
    *@author João Paulo e Leandro
    *@version 1.0
    *@return Candidato, devolve os dados do candidato
    */
    public Candidato getCandidato() {
        return candidato;
    }
    /**Método para 'setar' o candidato do voto
    *@author João Paulo e Leandro
    *@version 1.0
    *@param Candidato, instância do Candidato
    *@return void
    */
    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
}
