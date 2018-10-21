/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uteis;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author João Paulo e Leandro
 */
public class Arquivo {
    /**Método para ler a imagem que o usuário escolher como senha e transformá-la em matriz
    * @author João Paulo e Leandro
    * @param String, caminho do arquivo imagem escolhido pelo usuário
    * @return Integer[][], retorna a matriz de inteiros dos dados rgb da matriz
    * @version 1.0
    */
    public static Integer[][] leImagemMatriz(String caminho) {
        Integer matriz[][] = null;
        try {
            /*lê o arquivo, abre ele*/
            FileReader arq = new FileReader(caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            /*lê a primeira linha do conteúdo do arquivo*/
            String linha = lerArq.readLine();
            /*lê agora a senha linha*/
            linha = lerArq.readLine();
            /*pega os dados da linha, menos o espaço*/
            String vetor_string[] = linha.split(" ");
            int x = 0;
            int y = 0;
            /*verifica se os dados não são nulos, ou seja não leu de forma correta*/
            if (vetor_string != null) {
                /*se leu, seta os dados como os tamanhos da matriz*/
                x = Integer.parseInt(vetor_string[1]);
                /*y é multiplicado por 3, por causa dos valores RGB*/
                y = 3 * Integer.parseInt(vetor_string[0]);
            }
            linha = lerArq.readLine(); // Le a terceira linha
            /*instancia a matriz*/
            matriz = new Integer[x][y];
            for (int i = 0; i < x; i++) {
                /*e vai lendo os resto do arquivo*/
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
        /*no final retorna a matriz*/
        return matriz;
    }
    /**Método para criar um arquivo, conforme o nome e o conteúdo passado por paramêtro
     * @author João Paulo
     * @version 1.3
     * @param String, conteúdo que será escrito no arquivo
     * @param String, caminho(nome) do arquivo
     * @return void
     * @throws IOException, exceção caso o arquivo não consiga escrever no arquivo
     */
    public static void criaArquivo(String conteudo, String caminho) throws IOException {
        /*classe para criar arquivo com dados*/
        FileWriter arquivo;
        try {
            /*instancia a variavel*/
            arquivo = new FileWriter(new File(caminho));
            /*escreve os dados no arquivo*/
            arquivo.write(conteudo);
            /*e fecha o arquivo*/
            arquivo.close();
        /*caso não consiga lança uma exceção IO*/
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
    }
    /**Método para pegar os dados de um array de objetos e um nome(caminho) para o arquivo
     *@param ArrayList Object, lista de objetos
     *@param String, caminho do arquivo
     *@return void
     *@version 2.0
     */
    public static void criaArquivoJSON(ArrayList<Object> lista, String caminho) {
        Gson gson = new Gson();
        FileWriter arq = null;
        /*instancia o arquivo de escrita*/
        try {
            arq = new FileWriter(caminho);
        } catch (IOException ex) {
            ex.getMessage();
        }
        /*escreve no arquivo com o nome do arquivo passado por parametro*/
        PrintWriter gravarArq = new PrintWriter(arq);
        /*varre o arraylist, escrevendo os dados no arquivo*/
        for (Object l : lista) {
            /*escreve em formato JSON*/
            gravarArq.printf("%s\n", gson.toJson(l));
        }
        /*depois fecha o arquivo*/
        try {
            arq.close();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}
