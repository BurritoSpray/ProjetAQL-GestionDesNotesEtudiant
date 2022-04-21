package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Grade;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddGradeController {
    @FXML
    public TextField textFieldPoints, textFieldMaxPoints;
    @FXML
    public Button buttonConfirm;
    @FXML
    public RadioButton radioPercent, radioPoints;

    private Grade selectedGrade;
    private ManageGradeController manageGradeController;

    public void loadData(ManageGradeController manageGradeController, boolean newGrade){
        this.manageGradeController = manageGradeController;
        this.selectedGrade = newGrade ? new Grade() : manageGradeController.tableViewGrade.getSelectionModel().getSelectedItem();
        setupEvents();
        this.radioPoints.setSelected(true);
        this.textFieldPoints.setText(this.selectedGrade.getPoints()+"");
        this.textFieldMaxPoints.setText(this.selectedGrade.getMaxPoints()+"");
        setFocusOnPoints();

    }

    private void setupEvents(){
        this.radioPoints.getToggleGroup().getSelectedToggle().selectedProperty()
                .addListener((observableValue, aBoolean, t1) -> setPercentageMode(observableValue.getValue()));
        this.buttonConfirm.setOnAction(actionEvent -> onButtonConfirmClick());

        this.textFieldPoints.setOnKeyTyped(keyEvent -> {if(keyEvent.getCharacter().equals("\r"))this.buttonConfirm.fire();});
        this.textFieldMaxPoints.setOnKeyTyped(keyEvent -> {if(keyEvent.getCharacter().equals("\r"))this.buttonConfirm.fire();});
    }

    private void setPercentageMode(boolean enable){
        if(enable) {
            this.textFieldMaxPoints.setText("100");
            this.textFieldMaxPoints.setDisable(true);
        }else{
            this.textFieldMaxPoints.setDisable(false);
        }
    }

    private void onButtonConfirmClick(){
        Grade newGrade = new Grade();

        if(!this.textFieldPoints.getText().isEmpty() && !this.textFieldMaxPoints.getText().isEmpty()) {
            if (this.radioPoints.isSelected()) {
                newGrade = new Grade(Double.parseDouble(this.textFieldPoints.getText()),
                        Double.parseDouble(this.textFieldMaxPoints.getText()));
            }else{
                newGrade = new Grade(Double.parseDouble(this.textFieldPoints.getText()));
            }

            int index = Data.selectedStudent.getCourseList().indexOf(this.manageGradeController.selectedCourse);
            Student studentToUpdate = Data.selectedStudent;
            studentToUpdate.getCourseList().get(index).getGradeList().remove(this.selectedGrade);
            studentToUpdate.getCourseList().get(index).getGradeList().add(newGrade);
            this.manageGradeController.tableViewGrade.getItems().remove(this.selectedGrade);
            this.manageGradeController.tableViewGrade.getItems().add(newGrade);
            this.manageGradeController.labelCourseAverage.setText(
                    (int)this.manageGradeController.selectedCourse.getAverageInPercent()+"%");
            closeWindow();
        }
    }

    private void closeWindow(){
        ((Stage)this.buttonConfirm.getScene().getWindow()).close();
    }

    private void setFocusOnPoints(){
        Platform.runLater(() ->
                this.textFieldPoints.requestFocus());
    }
}
