package Threads;

import controller.MainController;
import javafx.application.Platform;
import utils.Pacote;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 18/02/2023
* Ultima alteracao.: 19/02/2023
* Nome.............: ThreadPacote
* Funcao...........: Classe responsavel por movimentar os pacotes
*************************************************************** */

public class ThreadPacote extends Thread{

  private MainController controller;
  private Pacote pacote;

  public ThreadPacote(Pacote p,MainController mc){
    pacote = p;
    controller = mc;
    
  }

  public void setController(MainController mc){
    controller = mc;
  }

  public void run(){
    
    Platform.runLater(() -> {
      controller.movimentaPacote(pacote.getEnderecoOrigem(),pacote.getEnderecoDestino());
    });
  }

  public void intervalo(int tempo){
    
    try {
      sleep(tempo);
    } catch (InterruptedException e) {e.printStackTrace();} 
  }
  
}
