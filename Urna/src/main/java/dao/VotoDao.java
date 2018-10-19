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
 * @author weth
 */
public class VotoDao {
    private ArrayList<Voto> votos;
    
    public VotoDao(){
        votos = new ArrayList();
    }
    
    public void cadastraVoto(Voto voto){
        this.votos.add(voto);
    }
    
    public ArrayList<Voto> retornaVotos(){
        return this.votos;
    }
}
