package ez.clap.gestionetudiant_aql.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageStudentWindow {
    @FXML
    public Button buttonCancel;
    @FXML
    public Button buttonConfirm;
    @FXML
    public TextField textFieldNumber, textFieldFirstName, textFieldSecondName;
    @FXML
    public static ListView<CheckBox> listViewCourse;

    @FXML
    private void onButtonConfirmClick(){

    }

    @FXML
    private void onButtonCancelClick(){
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    public void loadUser(String user){
        textFieldFirstName.setText(user);
    }


}
