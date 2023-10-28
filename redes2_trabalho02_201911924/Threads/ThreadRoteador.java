package Threads;

import controller.MainController;
import javafx.application.Platform;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 23/02/2023
* Ultima alteracao.: 05/03/2023
* Nome.............: MainController
* Funcao...........: Simular o roteador
*************************************************************** */

public class ThreadRoteador extends Thread{

  private MainController controller;
  private int roteador;
  

  public ThreadRoteador(int r){
    roteador = r;
  }


  public void setController(MainController c){
    this.controller = c;
  }

  public void run(){
    
    try{
      controller.changeImage(roteador);
      int destino = controller.getShortestPath().get(controller.getControl());
      Platform.runLater(() -> {
        this.controller.movePacket(roteador, destino);
      });
      interval(4000);
      
      
      controller.setControl(controller.getControl()+1);

      if(destino != roteador){
        controller.getRouters()[destino].start();
      }
    }catch(IndexOutOfBoundsException e){
      System.out.println("DESTINO ENCONTRADO");
    }
  }

   /* ***************************************************************
  * Metodo: interval
  * Funcao: Chama o metodo sleep
  * Parametros: Tempo de pausa
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void interval(int tempo){
    try {
      sleep(tempo);
    } catch (InterruptedException e) {
      
      e.printStackTrace();
    }
  }
}
  

