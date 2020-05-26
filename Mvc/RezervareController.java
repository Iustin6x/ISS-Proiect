package MVC;



import Model.Bilet;
import Model.Spectacol;
import Model.User;
import Service.BiletService;
import Service.SpectacolService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class RezervareController {


    private BiletService serv;
    Stage dialogStage;
    Scene scene;
    String spectacol_id,user_id;
    ArrayList<Bilet> bilete;
    ArrayList<Button> selected = new ArrayList<Button>();

    @FXML
    private void initialize() throws SQLException {
       serv=new BiletService();


    }

    public void initTable()
    {
        //Scene scene = dialogStage.getScene();

        for(int i=1;i<=5;i++)
        {
            for(int j=1;j<=4;j++)
            {
                Button btn = (Button) scene.lookup("#button"+i+j);
                if(serv.findByNR_Spectacol(i,j,spectacol_id)==null)
                {
                    btn.setStyle("-fx-background-color: #379F11");
                }
                else
                {
                    btn.setStyle("-fx-background-color: #D42323");
                    btn.setDisable(true);
                }

                //System.out.println("#button"+i+j);
                //btn.setStyle("-fx-background-color: #00ff00");
                //if(btn==null)
                   // System.out.println("#button"+i+j);
                    btn.setOnMouseClicked(e -> {
                        if(!selected.contains(btn)){
                            btn.setStyle("-fx-background-color: #BF8E1D");
                            selected.add(btn);
                        }else
                        {
                            btn.setStyle("-fx-background-color: #379F11");
                            selected.remove(btn);
                        }
                    });



            }
        }
    }


    public void setService(Stage stage,String spectacol_id,String user_id) {
        this.dialogStage=stage;
        scene=stage.getScene();
        if(this.scene==null)
        {
            System.out.println("nupi");
        }
        this.spectacol_id=spectacol_id;
        this.user_id=user_id;
        initTable();
    }

    @FXML
    public void rezerva(){
        try {
            selected.forEach(x->{

                String txt=x.getId();
                System.out.println(txt);
                int numar=Integer.parseInt(txt.substring(txt.length() - 2));
                try {
                    serv.save(new Bilet(numar/10,numar%10,spectacol_id,user_id));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Locuri rezervate cu succes");
                alert.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error");
            alert.setContentText("Ooops, there was an error!"+ex.getMessage());
            alert.showAndWait();
        }
        dialogStage.close();
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}


