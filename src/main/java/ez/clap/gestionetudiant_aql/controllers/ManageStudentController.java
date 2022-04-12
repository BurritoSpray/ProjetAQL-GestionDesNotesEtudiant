package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageStudentController {
    @FXML
    public Button buttonCancel;
    @FXML
    public Button buttonConfirm;
    @FXML
    public TextField textFieldNumber, textFieldFirstName, textFieldSecondName;
    @FXML
    public ListView<CheckBox> listViewCourse;
    @FXML
    private void onButtonCancelClick(){
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }


    public void loadStudent(Student student){
        textFieldFirstName.setText(student.getFirstName());
        textFieldSecondName.setText(student.getSecondName());
        textFieldNumber.setText(student.getStudentID());
        if(!student.getCourseList().isEmpty()){
            for(Course course : student.getCourseList()){
                listViewCourse.getItems().add(new CheckBox(course.getTitle()));
            }
        }
    }


}
