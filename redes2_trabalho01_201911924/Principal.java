import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 05/02/2023
* Ultima alteracao.: 18/02/2023
* Nome.............: Principal
* Funcao...........: Classe principal 
*************************************************************** */

public class Principal extends Application{
  public static void main(String[] args){
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
    MainController controlador = new MainController();
    fxmlloader.setController(controlador);
    
    Parent root = fxmlloader.load();
    Scene tela = new Scene(root);
    controlador.setCena(tela);

    primaryStage.setTitle("Tela Principal");
    primaryStage.setScene(tela);
    primaryStage.show();
  }
}
  