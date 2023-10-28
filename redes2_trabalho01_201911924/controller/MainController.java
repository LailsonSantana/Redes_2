package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import Threads.ThreadRoteador;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 05/02/2023
* Ultima alteracao.: 19/02/2023
* Nome.............: MainController
* Funcao...........: classe de controle principal
*************************************************************** */

public class MainController implements Initializable{

  @FXML
  private ChoiceBox<String> choiceOpcoes;


  @FXML
  private Text txtInfo;

  @FXML
  private Pane pane;

  @FXML
  private ImageView imgInfo,imgSobre;

  @FXML 
  private ImageView imgRouter0,imgRouter1,imgRouter2,imgRouter3,imgRouter4,imgRouter5,imgRouter6,imgRouter7
  ,imgRouter8,imgRouter9;

  private ThreadRoteador tRoteador0,tRoteador1,tRoteador2,tRoteador3,tRoteador4,tRoteador5,tRoteador6,tRoteador7,tRoteador8
  ,tRoteador9;

  private int num_roteadores = 10;

  private ThreadRoteador[] roteadores = new ThreadRoteador[num_roteadores];

  private ImageView[] imgRoteadores = new ImageView[num_roteadores];

  private ImageView imageOrigem,imageDestino = null;

  private int indiceOrigem;

  private int indiceDestino;

  private boolean origem = false;

  private String opcao = null;

  private Scene cena;

  private ArrayList<Integer>[] adjascentes = new ArrayList[num_roteadores];

  private String[] opcoes = {"Opcao 1","Opcao 2","Opcao 3","Opcao 4"};

  private static int cont;

  public MainController(){

  }

  public void setCena(Scene c){
    this.cena = c;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    
    criaAdjascencias();
    instanciaThreads();
    carregaRoteadores();
    

    for(int k=0;k<num_roteadores;k++){
      roteadores[k].setController(this);
    }
    choiceOpcoes.getItems().addAll(opcoes);

    
    carregaImgRoteadores();
    carregaCenario();
    selecionaOrigemDestino();;
   
  }

  /* ***************************************************************
  * Metodo: selecionaOrigemDestino
  * Funcao: Verifica os roteadores de origem e destino
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void selecionaOrigemDestino(){
    for(int i=0;i<roteadores.length;i++){
      final int index = i;
      imgRoteadores[i].setOnMouseClicked(event->{
        if(!origem){
          imageOrigem = imgRoteadores[index];
          indiceOrigem = index;
          origem = true;
          pisca(imageOrigem);
        }
        else{
          imageDestino = imgRoteadores[index];
          indiceDestino = index;
          // Evento a ser executado com origem e destino selecionados
          roteadores[indiceOrigem].adicionaPacote();
          roteadores[indiceOrigem].start();
          pisca(imageDestino);
        }
      });
    }
  }

  /* ***************************************************************
  * Metodo: pisca
  * Funcao: Realiza uma animacao no roteador selecionado
  * Parametros: Imagem na qual sera aplicada a animacao
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void pisca(ImageView iv){
    Timeline timeline = new Timeline(
      new KeyFrame(Duration.seconds(0), new KeyValue(iv.opacityProperty(), 0.0)),
      new KeyFrame(Duration.seconds(0.05), new KeyValue(iv.opacityProperty(), 1.0)),
      new KeyFrame(Duration.seconds(0.05), new KeyValue(iv.opacityProperty(), 0.0))
    );  
    timeline.play();     
  }

  /* ***************************************************************
  * Metodo: movimentaPacote
  * Funcao: Realiza a animacao do envio do pacote
  * Parametros: Dois inteiros , representando roteador de origem e destino
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void movimentaPacote(int origem,int destino){
    cont++;
    //System.out.println(cont);
    Image image = new Image("/images/mensagem.png");
    ImageView iv = new ImageView(image); 
    iv.setLayoutX(34);
    iv.setLayoutY(30);
    iv.setFitWidth(18);
    iv.setFitHeight(25);

    AnchorPane root = (AnchorPane) cena.getRoot();
    root.getChildren().add(iv); // Adiciona a imagem na cena

    TranslateTransition move = new TranslateTransition(Duration.millis(4000),iv);
     
    move.setFromY(imgRoteadores[origem].getLayoutY());
    move.setFromX(imgRoteadores[origem].getLayoutX());  

    move.setByY(imgRoteadores[destino].getLayoutY() - imgRoteadores[origem].getLayoutY());
    move.setByX(imgRoteadores[destino].getLayoutX() - imgRoteadores[origem].getLayoutX());
    
    move.play();
  }
  
  /* ***************************************************************
  * Metodo: carregaRoteadores
  * Funcao: Coloca as intancias das threads dentro de um vetor
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void carregaRoteadores(){
    roteadores[0] = tRoteador0;
    roteadores[1] = tRoteador1;
    roteadores[2] = tRoteador2;
    roteadores[3] = tRoteador3;
    roteadores[4] = tRoteador4;
    roteadores[5] = tRoteador5;
    roteadores[6] = tRoteador6;
    roteadores[7] = tRoteador7;
    roteadores[8] = tRoteador8;
    roteadores[9] = tRoteador9;
  }

  /* ***************************************************************
  * Metodo: carregaImgRoteadores
  * Funcao: Coloca as imagens dentro de um vetor
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void carregaImgRoteadores(){
    imgRoteadores[0] = imgRouter0;
    imgRoteadores[1] = imgRouter1;
    imgRoteadores[2] = imgRouter2;
    imgRoteadores[3] = imgRouter3;
    imgRoteadores[4] = imgRouter4;
    imgRoteadores[5] = imgRouter5;
    imgRoteadores[6] = imgRouter6;
    imgRoteadores[7] = imgRouter7;
    imgRoteadores[8] = imgRouter8;
    imgRoteadores[9] = imgRouter9;
  }

  /* ***************************************************************
  * Metodo: carregaCenario
  * Funcao: Carrega as imagens que farao parte da tela inicial
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void carregaCenario(){

    Image image = new Image("/images/router.png");
    
    imgRouter0.setImage(image);
    imgRouter1.setImage(image);
    imgRouter2.setImage(image);
    imgRouter3.setImage(image);
    imgRouter4.setImage(image);
    imgRouter5.setImage(image);
    imgRouter6.setImage(image);
    imgRouter7.setImage(image);
    imgRouter8.setImage(image);
    imgRouter9.setImage(image);
    
    for(int k=0;k<num_roteadores;k++){
      imgRoteadores[k].setImage(image);
    }
  }

  /* ***************************************************************
  * Metodo: criaAdjascencias
  * Funcao: Faz o mapeamento dos adjascentes de cada roteador
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void criaAdjascencias(){

    for(int i=0;i<num_roteadores;i++){
      adjascentes[i] = new ArrayList<>();
    }

    adjascentes[0].add(1);
    adjascentes[0].add(2);
    adjascentes[0].add(3);

    adjascentes[1].add(0);
    adjascentes[1].add(2);

    adjascentes[2].add(0);
    adjascentes[2].add(1);
    adjascentes[2].add(6);
    adjascentes[2].add(7);

    adjascentes[3].add(0);
    adjascentes[3].add(4);
    adjascentes[3].add(5);

    adjascentes[4].add(3);
    adjascentes[4].add(5);
    adjascentes[4].add(6);

    adjascentes[5].add(3);
    adjascentes[5].add(4);

    adjascentes[6].add(2);
    adjascentes[6].add(4);

    adjascentes[7].add(2);
    adjascentes[7].add(8);

    adjascentes[8].add(7);
    adjascentes[8].add(9);

    adjascentes[9].add(8);
    
  }

  /* ***************************************************************
  * Metodo: instanciaThreads
  * Funcao: Instancia as Threads dos roteadores
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void instanciaThreads(){
    tRoteador0 = new ThreadRoteador(0,adjascentes[0]);
    tRoteador1 = new ThreadRoteador(1,adjascentes[1]);
    tRoteador2 = new ThreadRoteador(2,adjascentes[2]);
    tRoteador3 = new ThreadRoteador(3,adjascentes[3]);
    tRoteador4 = new ThreadRoteador(4,adjascentes[4]);
    tRoteador5 = new ThreadRoteador(5,adjascentes[5]);
    tRoteador6 = new ThreadRoteador(6,adjascentes[6]);
    tRoteador7 = new ThreadRoteador(7,adjascentes[7]);
    tRoteador8 = new ThreadRoteador(8,adjascentes[8]);
    tRoteador9 = new ThreadRoteador(9,adjascentes[9]);
  }

  /* ***************************************************************
  * Metodo: selecionaOpcao
  * Funcao: Seleciona a opcao do algoritmo que sera executado
  * Parametros: ActionEvent event
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void selecionaOpcao(ActionEvent event){
    this.opcao = choiceOpcoes.getValue();
    Image image = new Image("/images/info.png");
    imgInfo.setImage(image);

    //escondeItens();
  }

   /* ***************************************************************
  * Metodo: sobre
  * Funcao: Mostra a informacao relacionada a execucao da opcao 4
  * Parametros: ActionEvent event
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void sobre(ActionEvent event){
    System.out.println("executado");
    Image image = new Image("/images/sobre.png");
    imgSobre.setImage(image);
  }

  /* ***************************************************************
  * Metodo: escondeItens
  * Funcao: Esconde alguns elementos
  * Parametros: ActionEvent event
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void escondeItens(){
    choiceOpcoes.setVisible(false);
    txtInfo.setVisible(false);
  }

  /* ***************************************************************
  * Metodo: stop
  * Funcao: Interrompe a execução de todas as Threads
  * Parametros: Nao possui parametros
  * Retorno: Nao possui retornos
  *************************************************************** */
  public void stop(){
    for(int i=0;i<roteadores.length;i++){
      roteadores[i].stop();
    }
  }

  public ThreadRoteador[] getRoteadores(){
    return this.roteadores;
  }

  public int getCont(){
    return cont;
  }

  public String getOpcao(){
    return opcao;
  }

  public int getIndiceDestino(){
    return indiceDestino;
  }

}
