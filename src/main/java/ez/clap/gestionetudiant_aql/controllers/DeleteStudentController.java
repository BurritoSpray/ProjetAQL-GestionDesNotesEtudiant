package ez.clap.gestionetudiant_aql.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteStudentController {
    @FXML
    public Button buttonConfirm, buttonCancel;

    @FXML
    private void onButtonCancelClick(){
        ((Stage)buttonCancel.getScene().getWindow()).close();
    }
}
