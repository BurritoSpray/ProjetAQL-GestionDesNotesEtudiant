package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private Button buttonAddStudent;

    @FXML
    private void onButtonAddStudentClick() throws IOException {
        Stage myStage = new Stage();
        myStage.setTitle("Ajouter un etudiant");
        Parent root = FXMLLoader.load(MainWindow.class.getResource("add-student-window.fxml"));
        myStage.setScene(new Scene(root));
        myStage.show();
    }
}