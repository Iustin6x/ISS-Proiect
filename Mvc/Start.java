package MVC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Start extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{


        //FXMLLoader messageLoader = new FXMLLoader();
        FXMLLoader  messageLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("MVC/AuthView.fxml"));
        //messageLoader.setLocation(getClass().getResource("MVC/AuthView.fxml"));
        AnchorPane messageTaskLayout = messageLoader.load();
        primaryStage.setScene(new Scene(messageTaskLayout));
        AuthController controller = messageLoader.getController();
        primaryStage.show();
    }





}