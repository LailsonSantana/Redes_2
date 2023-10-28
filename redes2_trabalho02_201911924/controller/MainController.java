package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Threads.ThreadRoteador;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import utils.Linkage;
import utils.ManipulateFile;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 23/02/2023
* Ultima alteracao.: 05/03/2023
* Nome.............: MainController
* Funcao...........: Classe de controle principal
*************************************************************** */

public class MainController implements Initializable{

  private static int NUM_ROUTERS;
  private ImageView[] imgRouters;
  private ThreadRoteador[] routers;
  private List<Integer> shortestPath;
  private int control = 1;
  private int sizePath = 0;
  private int indexOrigin,indexDestination;

  private boolean origin = false;
  
  @FXML
  private ImageView imgRouter1,imgRouter2,imgRouter3,imgRouter4,imgRouter5,imgRouter6,imgRouter7,imgRouter8;

  @FXML
  private ImageView imgSeta;
  
  
  private ThreadRoteador threadRoteador1,threadRoteador2,threadRoteador3,threadRoteador4,threadRoteador5,
                         threadRoteador6,threadRoteador7,threadRoteador8;
  private Scene cena;
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    
    ManipulateFile mf = new ManipulateFile();
    NUM_ROUTERS = mf.getLigacoes().get(0);
    loadImgRouters();
    loadThreadsRouters();
    selectOriginDestination();
  }

  public void setScene(Scene c){
    cena = c;
  }

  /* ***************************************************************
  * Metodo: startSimulation
  * Funcao: Inicia a simulacao
  * Parametros: Inteiros que representam o roteador de origem e destino
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void startSimulation(int o, int d){
    
    Linkage linka = new Linkage(o,d);
    
    shortestPath = linka.relates(); // executa a relacao entre as duas classes , gerando assim o menor caminho
    System.out.println();
    for(int i=0;i<shortestPath.size();i++){
      
      System.out.print((shortestPath.get(i)+1) + " ");
    }
    
    System.out.println("");
  }

  /* ***************************************************************
  * Metodo: selectOriginDestination
  * Funcao: Seleciona os roteadores de origem e destino
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void selectOriginDestination(){
    for(int i=0;i<routers.length;i++){
      final int index = i;
      imgRouters[i].setOnMouseClicked(event->{
        if(!origin){
          //imageOrigem = imgRouters[index];
          indexOrigin = index;
          origin = true;
        }
        else{
          //imageDestino = imgRouters[index];
          indexDestination = index;
          startSimulation(indexOrigin,indexDestination);
          
          this.getRouters()[indexOrigin].start(); // Inicia a thread depois da selecao
          origin = false;
          indexOrigin = -1;
          indexDestination = -1;
        }
      });
    }
  }

  /* ***************************************************************
  * Metodo: loadImgRouters
  * Funcao: Coloca as imagens dos roteadores em um vetor
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void loadImgRouters(){

    Image image = new Image("/images/router.png");
    Image image1 = new Image("/images/seta.png");
    imgRouters = new ImageView[NUM_ROUTERS];
    imgRouters[0] = imgRouter1;
    imgRouters[1] = imgRouter2;
    imgRouters[2] = imgRouter3;
    imgRouters[3] = imgRouter4;
    imgRouters[4] = imgRouter5;
    imgRouters[5] = imgRouter6;
    imgRouters[6] = imgRouter7;
    imgRouters[7] = imgRouter8;

    imgSeta.setImage(image1);

    for(int i=0;i<NUM_ROUTERS;i++){
      imgRouters[i].setImage(image);
    }
  }
  
  /* ***************************************************************
  * Metodo: loadThreadsRouters
  * Funcao: Coloca as threads dos roteadores em um vetor
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void loadThreadsRouters(){

    routers = new ThreadRoteador[NUM_ROUTERS];
    routers[0] = threadRoteador1;
    routers[1] = threadRoteador2;
    routers[2] = threadRoteador3;
    routers[3] = threadRoteador4;
    routers[4] = threadRoteador5;
    routers[5] = threadRoteador6;
    routers[6] = threadRoteador7;
    routers[7] = threadRoteador8;

    for(int i=0;i<NUM_ROUTERS;i++){
      routers[i] = new ThreadRoteador(i);
    }

    for(int k=0;k<NUM_ROUTERS;k++){
      routers[k].setController(this);
    }
  }

  /* ***************************************************************
  * Metodo: movePacket
  * Funcao: Faz a animacao de movimentar o pacote
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void movePacket(int origem,int destino){
    
    Image image = new Image("/images/pacote.png");
    ImageView iv = new ImageView(image); 
    iv.setLayoutX(34);
    iv.setLayoutY(30);
    iv.setFitWidth(18);
    iv.setFitHeight(25);

    AnchorPane root = (AnchorPane) cena.getRoot();
    root.getChildren().add(iv); // Adiciona a imagem na cena

    TranslateTransition move = new TranslateTransition(Duration.millis(4000),iv);
     
    move.setFromY(imgRouters[origem].getLayoutY());
    move.setFromX(imgRouters[origem].getLayoutX());  

    move.setByY(imgRouters[destino].getLayoutY() - imgRouters[origem].getLayoutY());
    move.setByX(imgRouters[destino].getLayoutX() - imgRouters[origem].getLayoutX());

    move.setOnFinished(event -> {
      root.getChildren().remove(iv); // Remove a imagem da cena após a transição
    });
  
    move.play();
  }

  /* ***************************************************************
  * Metodo: changeImage
  * Funcao: Muda a imagem que representa o roteador
  * Parametros: inteiro que representa o roteador
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void changeImage(int roteador){
    Image image = new Image("/images/router2.png");
    imgRouters()[roteador].setImage(image);
  }

  /* ***************************************************************
  * Metodo: newSimulation
  * Funcao: Recarrega o programa para uma nova simulacao
  * Parametros: Evento de clique
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void newSimulation(ActionEvent event){
    ManipulateFile mf = new ManipulateFile();
    NUM_ROUTERS = mf.getLigacoes().get(0);
    loadImgRouters();
    loadThreadsRouters();
    selectOriginDestination();
    this.control = 1;
  }

  public List<Integer> getShortestPath(){
    return this.shortestPath;
  }

  public ThreadRoteador[] getRouters() {
    return routers;
  }

  public ImageView[] imgRouters(){
    return imgRouters;
  }

  public int getControl(){
    return this.control;
  }

  public void setControl(int v){
    this.control = v;
  }
}
