package Threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import controller.MainController;
import utils.Pacote;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 05/02/2023
* Ultima alteracao.: 19/02/2023
* Nome.............: ThreadRoteador
* Funcao...........: Classe que fara o papel de cada roteador
*************************************************************** */

public class ThreadRoteador extends Thread{

  private MainController controller;
  private BlockingQueue<Pacote> filaDePacotes;
  private ThreadPacote tp;
  private boolean ativaTTL = false;
  private ArrayList<Integer> vizinhos;
  private int roteadorId;
  private int pacotesEnviados;
  private int quantMax;
  private String opcao;

  
  public ThreadRoteador(int r,ArrayList<Integer> a){
    roteadorId = r;
    vizinhos = a;
    filaDePacotes = new ArrayBlockingQueue<>(10);
    pacotesEnviados = 0;
    quantMax = 1000;
    
  }

  public ThreadRoteador(){
    pacotesEnviados = 0;
    quantMax = 1000;
  }

  public void setController(MainController c){

    this.controller = c;
  }

  public void setOpcao(String opc){
    opc = opcao;
  }

  public void run(){
    opcao = controller.getOpcao();

    if(controller.getIndiceDestino() == roteadorId){
      controller.stop();
    }
    while(true){
      
      try{
      Pacote pacote = null;
      try { pacote = filaDePacotes.take(); } catch (InterruptedException e) {System.out.println("Fila Exception");}
      // Recebe um pacote se estiver na fila , se nao fica aguardando
      if(pacote != null){
        if(pacote.getTtl() > 0 || ativaTTL == false){
          pacote.subtrairTTL();
          
          chamaThreadPacote(pacote);
          intervalo(4300);

          for(int i=0;i<vizinhos.size();i++){
            
            if(vizinhos.get(i) != pacote.getEnderecoOrigem()){ // Evita que seja enviado para quem enviou
              pacotesEnviados++;
              int destino = vizinhos.get(i);
              Pacote pac = new Pacote(roteadorId,destino,pacote.getTtl());

              try {
                controller.getRoteadores()[destino].filaDePacotes.put(pac);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

              if(controller.getRoteadores()[destino].isAlive() == false){
                controller.getRoteadores()[destino].start(); // Caso nao esteja ativo
              }
            }
            if(pacotesEnviados >= quantMax){
              break;
            }
          }
        }
      }
      }catch(Exception e){
        System.out.println("DESTINO ENCONTRADO");
      }
    }
  }

  /* ***************************************************************
  * Metodo: chamaThreadPacote
  * Funcao: Chamar a thread pacote e executar o codigo da opcao escolhida
  * Parametros: Pacote que sera enviado
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void chamaThreadPacote(Pacote pac){
    if(opcao == "Opcao 1"){
      for(int i=0;i<vizinhos.size();i++){
          int destino = vizinhos.get(i);
          Pacote p = new Pacote(roteadorId,destino,pac.getTtl());
          tp = new ThreadPacote(p,controller);
          tp.start();
      }
    }
    else if(opcao == "Opcao 2"){
      for(int i=0;i<vizinhos.size();i++){
        if(vizinhos.get(i) != pac.getEnderecoOrigem()){
          int destino = vizinhos.get(i);
          Pacote p = new Pacote(roteadorId,destino,pac.getTtl());
          tp = new ThreadPacote(p,controller);
          tp.start();
        }
      }
    }
    else if(opcao == "Opcao 3"){
      ativaTTL = true;
      for(int i=0;i<vizinhos.size();i++){
        if(vizinhos.get(i) != pac.getEnderecoOrigem()){
          int destino = vizinhos.get(i);
          Pacote p = new Pacote(roteadorId,destino,pac.getTtl());
          tp = new ThreadPacote(p,controller);
          tp.start();
        }
      }
    } 
    else if(opcao == "Opcao 4"){
      ativaTTL = true;
      quantMax = 10; // Cada roteador pode somente enviar 10 pacotes no maximo
      for(int i=0;i<vizinhos.size();i++){
        if(vizinhos.get(i) != pac.getEnderecoOrigem()){
          int destino = vizinhos.get(i);
          Pacote p = new Pacote(roteadorId,destino,pac.getTtl());
          tp = new ThreadPacote(p,controller);
          tp.start();
        }
      }
    }
  }

  /* ***************************************************************
  * Metodo: adicionaPacote
  * Funcao: Iniciar a fila de pacotes
  * Parametros: Pacote que sera enviado
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void adicionaPacote(){
    this.filaDePacotes.add(new Pacote(roteadorId,-1,5)); // TTL definida como 5
  }
  
  /* ***************************************************************
  * Metodo: intervalo
  * Funcao: Chamar o metodo sleep
  * Parametros: Inteiro simbolizando o tempo de parada
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void intervalo(int tempo){
    try {
      sleep(tempo);
    } catch (InterruptedException e) {e.printStackTrace();} 
  }
} 

  

