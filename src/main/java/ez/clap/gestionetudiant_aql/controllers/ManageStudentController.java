package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ManageStudentController {
    @FXML
    public Button buttonCancel;
    @FXML
    public Button buttonConfirm;
    @FXML
    public TextField textFieldNumber, textFieldFirstName, textFieldSecondName;
    @FXML
    public ListView<CheckBox> listViewCourse;

    private Student selectedStudent;

    @FXML
    private void onButtonConfirmClick(){
        addStudentIfValid();
    }

    @FXML
    private void onButtonCancelClick(){
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    private void loadStudent(){
        textFieldFirstName.setText(this.selectedStudent.getFirstName());
        textFieldSecondName.setText(this.selectedStudent.getSecondName());
        textFieldNumber.setText(this.selectedStudent.getStudentID());
        if(!this.selectedStudent.getCourseList().isEmpty()){
            for(Course course : this.selectedStudent.getCourseList()){
                listViewCourse.getItems().add(new CheckBox(course.getTitle()));
            }
        }
    }

    private void setupEvents(){
        buttonConfirm.setOnAction(actionEvent -> onButtonConfirmClick());
    }

    private void loadCourseList(){
        for(Course course : Data.getCourseList()){
            CheckBox newCheckBox = new CheckBox();
            newCheckBox.setText(course.getTitle());
            if(this.selectedStudent.getCourseList().contains(course)){
                newCheckBox.setSelected(true);
            }
            listViewCourse.getItems().add(newCheckBox);
        }
    }

    public void loadData(Student student){
        this.selectedStudent = student;
        loadStudent();
        loadCourseList();
        setupEvents();
    }
    
    private void addStudent(){
        ArrayList<Course> studentCourseList = new ArrayList<>();
        for(int i = 0; i < listViewCourse.getItems().size(); i++){
            if(listViewCourse.getItems().get(i).isSelected()){
                studentCourseList.add(Data.getCourseList().get(i));
            }
        }
        Student student = new Student(this.textFieldFirstName.getText(),
                this.textFieldSecondName.getText(),
                this.textFieldNumber.getText(), studentCourseList);
        Data.getStudentList().remove(selectedStudent);
        Data.getStudentList().add(student);
    }
    
    private void addStudentIfValid(){
        if (isStudentFieldsValid()) {
            addStudent();
            closeWindow();
        } else {
            showWarningPopup("Erreur", "Information manquante!", "OK");
        }
    }
    
    private boolean isStudentFieldsValid(){
        return !this.textFieldFirstName.getText().isEmpty() &&
                !this.textFieldSecondName.getText().isEmpty() &&
                !this.textFieldNumber.getText().isEmpty();
    }
    
    private void closeWindow(){
        ((Stage)this.buttonConfirm.getScene().getWindow()).close();
    }

    private void showWarningPopup(String title, String warningMessage, String buttonText){
        Dialog dialog = new Dialog();
        dialog.setTitle(title);
        dialog.setContentText(warningMessage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType closeButton = new ButtonType(buttonText, ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.show();
    }

}
