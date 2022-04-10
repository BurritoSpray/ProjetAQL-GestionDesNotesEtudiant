package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageCourseController {
    @FXML
    public Button buttonConfirm, buttonCancel;

    @FXML
    public TextField textFieldNumber, textFieldCode, textFieldTitle;

    @FXML
    private void onButtonConfirmClick(){

    }

    @FXML
    private void onButtonCancelClick(){
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    public void loadCourse(Course course){
        textFieldNumber.setText(course.getCourseNumber());
        textFieldCode.setText(course.getCode());
        textFieldTitle.setText(course.getTitle());
    }

}
