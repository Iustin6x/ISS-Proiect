package MVC;



import Model.Spectacol;
import Service.SpectacolService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SpectacolController {
    @FXML
    private TextField titlu_field;
    @FXML
    private TextField descriere_field;
    @FXML
    private TextField data_field;
    @FXML
    private TextField pretBilet_field;

    private SpectacolService serv;
    Stage dialogStage;
    Spectacol spectacol;

    @FXML
    private void initialize() throws SQLException {
    }


    public void setService(Stage stage,SpectacolService service, Spectacol s) {
        this.serv=service;
        this.dialogStage=stage;
        this.spectacol=s;
        if (null != s) {
            setFields(s);
        }
    }
    public void setService(Stage stage,SpectacolService service) {
        this.serv=service;
        this.dialogStage=stage;
    }

    @FXML
    public void handleSave(){
        String titlu=titlu_field.getText();
        String descriere=descriere_field.getText();
        Float pret=Float.parseFloat(pretBilet_field.getText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime datasiora = LocalDateTime.parse(data_field.getText(), formatter);
        //LocalDateTime datasiora=LocalDateTime.parse(data_field.getText());

        if (null == this.spectacol) {
            Spectacol s=new Spectacol(titlu,descriere,datasiora,pret);
            save(s);
        }
        else {
            Spectacol s=new Spectacol(spectacol.getId(),titlu,descriere,datasiora,pret);
            update(s);
        }

    }

    private void update(Spectacol s)
    {
        try {
            Spectacol spec= this.serv.update(s);
            if (spec==null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Spectacol modificat cu succes");
                alert.showAndWait();}
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error");
            alert.setContentText("Ooops, there was an error!"+ex.getMessage());
            alert.showAndWait();
        }
        dialogStage.close();
    }


    private void save(Spectacol s)
    {
        try {
            Spectacol spec= this.serv.save(s);
            if (spec==null) {
                dialogStage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Spectacol adaugat cu succes");
                alert.showAndWait();
            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error");
            alert.setContentText("Ooops, there was an error!"+ex.getMessage());
            alert.showAndWait();
        }

    }

    private void clearFields() {
        titlu_field.setText("");
        descriere_field.setText("");
        data_field.setText("");
        pretBilet_field.setText("");
    }
    private void setFields(Spectacol s)
    {
        titlu_field.setText(s.getId());
        descriere_field.setText(s.getDescriere());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = s.getDataSiora().format(formatter);
        data_field.setText(formattedDateTime);
        pretBilet_field.setText(""+s.getPretBilet());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}

