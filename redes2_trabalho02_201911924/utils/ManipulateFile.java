package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 23/02/2023
* Ultima alteracao.: 05/03/2023
* Nome.............: ManipulateFile
* Funcao...........: Extrai os dados do arquivo backbone.txt
*************************************************************** */

public class ManipulateFile {

  private ArrayList<Integer> ligacoes;

  public ManipulateFile(){
    openFile();
  }

  public void openFile(){
    try{
      String arq = "backbone.txt";
      BufferedReader br = new BufferedReader(new FileReader(arq));
      ligacoes = new ArrayList<>();
      String line;
      String[] valores;
      while ((line = br.readLine()) != null) {
        valores = line.split(";");
        for(String valor : valores){
          ligacoes.add(Integer.parseInt(valor));
        }
      }
      br.close();
    }catch(IOException e){
      e.getMessage();
    }
  }

  public ArrayList<Integer> getLigacoes(){
    return ligacoes;
  }
}
