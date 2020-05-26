package MVC;

import MVC.events.Observer;
import MVC.events.SpectacolChangeEvent;
import Model.Spectacol;
import Service.SpectacolService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController implements Initializable, Observer<SpectacolChangeEvent> {
    ObservableList<Spectacol> model = FXCollections.observableArrayList();
    SpectacolService serv;
    private String type,user;

    @FXML
    private TableView<Spectacol> tableView;
    @FXML
    private TableColumn<Spectacol, String> titlu;
    @FXML
    private TableColumn<Spectacol, String> descriere;
    @FXML
    private TableColumn<Spectacol, LocalDateTime> data;
    @FXML
    private TableColumn<Spectacol, Float> pretBilet;
    @FXML
    private Button rezerva_btn;
    @FXML
    private Button adauga_btn;
    @FXML
    private Button sterge_btn;
    @FXML
    private Button modifica_btn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            serv=new SpectacolService();
            serv.addObserver(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        titlu.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("titlu"));
        data.setCellValueFactory(new PropertyValueFactory<Spectacol, LocalDateTime>("dataSiora"));
        descriere.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("descriere"));
        pretBilet.setCellValueFactory(new PropertyValueFactory<Spectacol, Float>("pretBilet"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        data.setCellFactory(column -> new TableCell<Spectacol, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(formatter.format(date));
                }
            }
        });

        tableView.setItems(model);
        initModel();

    }

    private void initModel() {
        List<Spectacol> list= StreamSupport.stream(serv.printAll_disponibile().spliterator(), false)
                .collect(Collectors.toList());
       model.setAll(list);
    }


    public void init(String user,String type)
    {
        this.type=type;
        this.user=user;
        //System.out.println(type);
        if(type.equals("Manager"))
        {
            rezerva_btn.setVisible(false);
        }
        if(type.equals("Spectator"))
        {
            adauga_btn.setVisible(false);
            sterge_btn.setVisible(false);
            modifica_btn.setVisible(false);
        }
    }
    @FXML
    public void add(ActionEvent actionEvent) throws IOException{
        showSpectacolDialog(null);
    }
    @FXML
    public void delete(ActionEvent actionEvent) throws IOException, SQLException {
        Spectacol selected = (Spectacol) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Spectacol deleted = serv.delete(selected.getId());
            if (null != deleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sters cu succes");
                alert.showAndWait();
            }
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error");
            alert.setContentText("Nu a fost selectat nici un spectacol!");
            alert.showAndWait();}
    }
    @FXML
    public void update(ActionEvent actionEvent) throws IOException{
        Spectacol selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showSpectacolDialog(selected);
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error");
            alert.setContentText("Nu a fost selectat nici un spectacol!");
            alert.showAndWait();
        }
    }

    @FXML
    public void rezerva(ActionEvent actionEvent) throws IOException {
        Spectacol selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showRezervareDialog(selected.getId());
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error");
            alert.setContentText("Nu a fost selectat nici un spectacol!");
            alert.showAndWait();
        }
    }
    public void showSpectacolDialog(Spectacol spectacol) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SpectacolView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Spectacol Dialog");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            SpectacolController spectacolController = loader.getController();
            if (spectacol != null)
                spectacolController.setService(dialogStage,serv, spectacol);
            else
                spectacolController.setService(dialogStage,serv);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void showRezervareDialog(String spectacol_id) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RezervareView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Rezervare Dialog");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RezervareController rezController = loader.getController();
            rezController.setService(dialogStage,spectacol_id,user);
            if(scene==null)
                System.out.println("da11");
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(SpectacolChangeEvent spectacolChangeEvent) {
        initModel();
    }
}

