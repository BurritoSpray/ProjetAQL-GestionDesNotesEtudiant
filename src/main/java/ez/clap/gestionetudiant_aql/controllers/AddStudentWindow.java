package ez.clap.gestionetudiant_aql.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

public class AddStudentWindow {
    @FXML
    private Button buttonAddCourse;
    @FXML
    private ListView<CheckBox> listViewCourse;

    @FXML
    private void onButtonClick(){
        System.out.println("Hello World!");
    }

    @FXML
    private void onButtonAddCourseClick(){
        listViewCourse.getItems().add(new CheckBox("A Course"));
    }
}
