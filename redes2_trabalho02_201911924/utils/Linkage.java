package utils;

import java.util.ArrayList;
import java.util.List;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 23/02/2023
* Ultima alteracao.: 05/03/2023
* Nome.............: ShortestPath
* Funcao...........: Classe que faz a busca pelo menor caminho
*************************************************************** */

public class Linkage {

  private static int MAX_INT = 1000000;
  private ShortestPath shortestPath;
  private ManipulateFile manipulateFile;
  private int matrizAdjascencia[][];
  private int numRoteadores;
  private int roteadorOrigem;
  private int roteadordestino;
  private ArrayList<Integer> ligacoes = new ArrayList<>();


  public Linkage(int roteadorOrigem,int roteadorDestino){

    manipulateFile = new ManipulateFile();
    shortestPath = new ShortestPath();
    numRoteadores = manipulateFile.getLigacoes().get(0);
    this.roteadorOrigem = roteadorOrigem;
    this.roteadordestino = roteadorDestino;
    
  }

  public int getNumRouters(){
    return numRoteadores;
  }

  public List<Integer> relates(){
    createAdjacenceMatrix();
    List<Integer> d = shortestPath.shortestPath(matrizAdjascencia, roteadorOrigem, roteadordestino);
    return d; 
  }

  /* ***************************************************************
  * Metodo: createAdjacenceMatrix
  * Funcao: Cria a  matriz de adjascencia
  * Parametros: Nao possui parametros 
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void createAdjacenceMatrix(){

    manipulateFile.openFile();
    ligacoes = manipulateFile.getLigacoes();
    numRoteadores = ligacoes.get(0);

    matrizAdjascencia = new int[numRoteadores][numRoteadores];
    int cont = 0;
    int origem = 0;
    int destino = 0;
    int peso;

    for(int i=1;i<ligacoes.size();i++){
      
      cont++;
      if(cont == 1){
        origem = ligacoes.get(i);
      }
      else if(cont == 2){
        destino = ligacoes.get(i);
      }
      else{
        peso = ligacoes.get(i);
        matrizAdjascencia[origem-1][destino-1] = peso;
        matrizAdjascencia[destino-1][origem-1] = peso;
        matrizAdjascencia[origem-1][origem-1] = -10;
        matrizAdjascencia[destino-1][destino-1] = -10;
        cont = 0;
      }
    }

    for(int i=0;i<numRoteadores;i++){
      for(int k=0;k<numRoteadores;k++){
        if(matrizAdjascencia[i][k] == 0){
          matrizAdjascencia[i][k] = MAX_INT;
        }
        else if(matrizAdjascencia[i][k] == -10){
          matrizAdjascencia[i][k] = 0;
        }
      }
    }
  }
}
