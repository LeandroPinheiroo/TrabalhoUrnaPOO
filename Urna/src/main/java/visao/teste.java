/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.google.api.services.drive.model.File;
import conexao.ConexaoDrive;
import java.util.List;
/**
 *
 * @author leandro
 */
public class teste {
    public static void main(String[] args) {
        ConexaoDrive.getInstance();
        List<File> lista  = ConexaoDrive.listaArquivos();
        System.out.println("ID =" + lista.toString());
    }
}
