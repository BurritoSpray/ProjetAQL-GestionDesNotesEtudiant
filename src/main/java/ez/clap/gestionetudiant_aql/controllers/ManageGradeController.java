package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Grade;
import ez.clap.gestionetudiant_aql.entities.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManageGradeController
{
    @FXML
    public Button buttonAddGrade, buttonEditGrade, buttonDeleteGrade;
    @FXML
    public ComboBox<String> comboBoxCourse;
    @FXML
    public TableView<Grade> tableViewGrade;
    @FXML
    public TableColumn<Grade, String> tableColumnGrade, tableColumnGradePercent;
    @FXML
    public Label labelStudentNumber, labelStudentFirstName, labelStudentSecondName,
            labelCourseTitle, labelCourseNumber, labelCourseCode, labelCourseAverage;

    private Student selectedStudent;

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
        this.selectedStudent = student;
        setupEvents();
        setStudentInfo(student);
        if(!student.getCourseList().isEmpty())
            this.comboBoxCourse.getSelectionModel().selectFirst();
        else
            setCourseInfo(new Course());

    }

    private void setupEvents(){
        this.comboBoxCourse.selectionModelProperty().addListener((observableValue, stringSingleSelectionModel, t1) -> {
            setCourseInfo(selectedStudent.getCourseList().get(t1.getSelectedIndex()));
        });

        this.tableViewGrade.selectionModelProperty().addListener((observableValue, gradeTableViewSelectionModel, t1) -> {

        });
    }

    private void setStudentInfo(Student student){
        if(!student.getCourseList().isEmpty())
            this.comboBoxCourse.setItems(student.getCourseListAsComboBox().getItems());
        this.labelStudentNumber.setText(student.getStudentID());
        this.labelStudentFirstName.setText(student.getFirstName());
        this.labelStudentSecondName.setText(student.getSecondName());
    }

    private void setCourseInfo(Course course){
        this.labelCourseCode.setText(course.getCode());
        this.labelCourseNumber.setText(course.getCourseNumber());
        this.labelCourseTitle.setText(course.getTitle());
        this.labelCourseAverage.setText(course.getAverageInPercent() + "%");
    }

    private void setupTableView(){
        this.tableColumnGrade.setCellValueFactory(new PropertyValueFactory<Grade, String>("Grade"));
        this.tableColumnGradePercent.setCellValueFactory(new PropertyValueFactory<Grade, String>("GradeInPercent"));
    }
}
