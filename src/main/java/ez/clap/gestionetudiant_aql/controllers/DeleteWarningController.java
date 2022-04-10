package ez.clap.gestionetudiant_aql.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteWarningController {
    @FXML
    public Button buttonConfirm, buttonCancel;
    @FXML
    public Label labelStudentName;

    @FXML
    private void onButtonCancelClick(){
        ((Stage)buttonCancel.getScene().getWindow()).close();
    }
}
