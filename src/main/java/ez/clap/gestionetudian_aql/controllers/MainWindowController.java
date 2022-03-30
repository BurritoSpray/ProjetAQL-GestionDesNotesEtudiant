package ez.clap.gestionetudian_aql.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MainWindowController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button myButton;
    @FXML
    private ListView<String> listView;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onMyFuncClick(){
        listView.getItems().add("Salut");

    }
}