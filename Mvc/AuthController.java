package MVC;

import Model.User;
import Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.tree.ExpandVetoException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AuthController implements Initializable {
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private Button loginButton;

    private UserService userService;

    @FXML
    public void login(ActionEvent event) throws IOException, SQLException {

        try{
            User usr= userService.check(user.getText(), password.getText());
            //if (user!=null)
            String result=userService.login(usr);
            if(result!=null){
                Stage appStage;
                Parent root;
                appStage = (Stage) loginButton.getScene().getWindow();

                initMain(appStage,result,user.getText());
                appStage.setTitle("REZERVARE TEATRU");
                appStage.setWidth(700);
                appStage.show();
        }else
            {
                throw new Exception("wrong password");
            }
        }catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error");
            alert.setContentText("Ooops, there was an error!"+ex.getMessage());
            alert.showAndWait();
        }
    }
    private void initMain(Stage primaryStage,String type,String user_id) throws IOException, SQLException {
        userService = new UserService();
        FXMLLoader  messageLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("MVC/MainView.fxml"));
        AnchorPane messageTaskLayout = messageLoader.load();
        primaryStage.setScene(new Scene(messageTaskLayout));

        MainController  controller = messageLoader.getController();
        controller.init(user_id,type);

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userService=new UserService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}