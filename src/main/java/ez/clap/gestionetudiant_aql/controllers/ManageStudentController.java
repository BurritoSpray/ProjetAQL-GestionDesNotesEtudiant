package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private MainWindowController mainWindowController;


    private void loadStudent(){
        textFieldFirstName.setText(this.selectedStudent.getFirstName());
        textFieldSecondName.setText(this.selectedStudent.getSecondName());
        textFieldNumber.setText(this.selectedStudent.getStudentID());
    }

    private void setupEvents(){
        this.buttonConfirm.setOnAction(actionEvent -> addStudentIfValid());
        this.buttonCancel.setOnAction(actionEvent -> closeWindow());
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

    public void loadData(MainWindowController mainWindowController, boolean newStudent){
        this.mainWindowController = mainWindowController;
        this.selectedStudent = newStudent ? new Student() : mainWindowController.tableViewStudent.getSelectionModel().getSelectedItem();
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
        mainWindowController.tableViewStudent.refresh();
    }
    
    private void addStudentIfValid(){
        if (isStudentFieldsValid()) {
            addStudent();
            closeWindow();
        } else {
            this.mainWindowController.showWarningPopup("Erreur", "Information manquante!", "OK");
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


}
