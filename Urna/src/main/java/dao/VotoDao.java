/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import modelo.Voto;

/**
 *
 * @author João Paulo e Leandro
 */
public class VotoDao {
    /*Arraylist para conter os votos*/
    private ArrayList<Voto> votos;
    
    /*Construtor da classe*/
    public VotoDao(){
        /*Nela o arraylist é instanciado*/
        votos = new ArrayList();
    }
    /**Método para adicionar os votos no arraylist de votos
    *@author João Paulo e Leandro
    *@param Voto, recebe a instância do voto para ser adicionado a lista de votos
    *@return void
    *@version 1.0
    */
    public void cadastraVoto(Voto voto){
        this.votos.add(voto);
    }
    /**Método para retornar os votos no arraylist de votos
    *@author João Paulo e Leandro
    *@return ArrayList Voto, retorna o arraylist de votos
    *@version 1.0
    */
    public ArrayList<Voto> retornaVotos(){
        return this.votos;
    }
}
