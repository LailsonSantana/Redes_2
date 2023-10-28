import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application{
  public static void main(String[] args){
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
    MainController controller = new MainController();
    fxmlloader.setController(controller);

    Parent root = fxmlloader.load();
    Scene tela = new Scene(root);
    controller.setScene(tela);

    primaryStage.setTitle("Tela Principal");
    primaryStage.setScene(tela);
    primaryStage.show();
  }
}
  