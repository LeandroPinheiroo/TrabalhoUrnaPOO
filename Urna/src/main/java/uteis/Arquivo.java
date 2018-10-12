/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uteis;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 *
 * @author weth
 */
public class Arquivo {

    public static Integer[][] leImagemMatriz(String caminho) {
        Integer matriz[][] = null;
        try {
            FileReader arq = new FileReader(caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine(); // lê a primeira linha
            linha = lerArq.readLine(); // lê a segunda linha contendo o tamanho da matriz
            String vetor_string[] = linha.split(" ");
            int x = 0;
            int y = 0;
            if (vetor_string != null) {
                x = Integer.parseInt(vetor_string[1]);
                y = 3 * Integer.parseInt(vetor_string[0]);
            }
            linha = lerArq.readLine(); // Le a terceira linha
            matriz = new Integer[x][y];
            for (int i = 0; i < x; i++) {
                linha = lerArq.readLine();// le da quarta linha em diante
                String vet_imagem[] = linha.split(" ");//quebra a linha para pegar cada elemento
                for (int j = 0; j < y; j++) {
                    matriz[i][j] = Integer.parseInt(vet_imagem[j]);//joga o valor do elemento na matriz
                }
            }
            arq.close();//fecha o arquivo para evitar erro
        } catch (IOException e) {//captura o erro
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        return matriz;
    }

    public static void criaArquivo(String conteudo, String caminho) throws IOException {
        FileWriter arquivo;
        try {
            arquivo = new FileWriter(new File(caminho));
            arquivo.write(conteudo);
            arquivo.close();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void criaArquivoJSON(ArrayList<Object> lista, String caminho) {
        Gson gson = new Gson();
        FileWriter arq = null;
        try {
            arq = new FileWriter(caminho);
        } catch (IOException ex) {
            ex.getMessage();
        }
        PrintWriter gravarArq = new PrintWriter(arq);
        for (Object l : lista) {
            gravarArq.printf("%s", gson.toJson(l));
        }
        try {
            arq.close();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

}
