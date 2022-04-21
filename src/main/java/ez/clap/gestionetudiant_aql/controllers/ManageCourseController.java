package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageCourseController {
    @FXML
    public Button buttonConfirm, buttonCancel;

    @FXML
    public TextField textFieldNumber, textFieldCode, textFieldTitle;

    private Course selectedCourse;
    private MainWindowController mainWindowController;

    @FXML
    private void onButtonConfirmClick(){

    }

    @FXML
    private void onButtonCancelClick(){
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    public void loadCourse(MainWindowController mainWindowController, boolean newCourse){
        this.mainWindowController = mainWindowController;
        if(newCourse){
            this.selectedCourse = new Course();
            this.buttonConfirm.setText("Modifier");
        }else
            this.selectedCourse = this.mainWindowController.tableViewCourse.getSelectionModel().getSelectedItem();
        textFieldNumber.setText(this.selectedCourse.getCourseNumber());
        textFieldCode.setText(this.selectedCourse.getCode());
        textFieldTitle.setText(this.selectedCourse.getTitle());
        setupEvents();
    }

    private void setupEvents(){
        this.buttonConfirm.setOnAction(actionEvent -> this.addCourse());
        this.buttonCancel.setOnAction(actionEvent -> this.closeWindow());
    }

    private boolean isCourseFieldsValid(){
        return !this.textFieldNumber.getText().isEmpty() &&
                !this.textFieldCode.getText().isEmpty() &&
                !this.textFieldTitle.getText().isEmpty();
    }

    private void addCourse(){
        if(this.isCourseFieldsValid()){
            Course course = new Course(this.textFieldTitle.getText(),
                    this.textFieldCode.getText(),
                    this.textFieldNumber.getText());
            Data.getCourseList().add(course);
            this.closeWindow();
        }else{
            this.mainWindowController.showWarningPopup("Erreur", "Information manquante", "OK");
        }
    }

    private void closeWindow(){
        ((Stage)this.buttonCancel.getScene().getWindow()).close();
    }

}
