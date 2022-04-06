package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Grade;
import ez.clap.gestionetudiant_aql.entities.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class ManageGradeController
{
    @FXML
    public Button buttonAddGrade, buttonEditGrade, buttonDeleteGrade;
    @FXML
    public ComboBox<String> comboBoxCourse;
    @FXML
    public TableView<Grade> tableViewGrade;

    @FXML
    public void onButtonAddGradeClick(){

    }

    @FXML
    public void onButtonEditGradeClick(){

    }

    @FXML
    public void onButtonDeleteGradeClick(){

    }

    public void loadData(Student student){

    }
}
