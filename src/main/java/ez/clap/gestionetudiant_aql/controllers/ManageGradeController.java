package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.MainWindow;
import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Grade;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageGradeController
{
    enum Action{
        ADD_GRADE,
        EDIT_GRADE,
        DELETE_GRADE
    }
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

    public Student selectedStudent;
    public Course selectedCourse;
    private MainWindowController mainWindowController;

    public void loadData(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
        this.selectedStudent = mainWindowController.tableViewStudent.getSelectionModel().getSelectedItem();
        Data.selectedStudent = this.selectedStudent;
        setupEvents();
        setStudentInfo(this.selectedStudent);
        this.comboBoxCourse.getSelectionModel().selectFirst();
        setCourseInfo(this.selectedStudent.getCourseList().isEmpty() ? new Course() : this.selectedStudent.getCourseList().get(0));
        setupTableView();
    }

    private Stage setupGradeWindow(Action action){
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("add-grade-window.fxml"));
            Parent root = loader.load();
            AddGradeController addGradeController = loader.getController();

            switch (action){
                case ADD_GRADE -> {
                    stage.setTitle("Nouvelle note");
                    addGradeController.loadData(this, true);
                }
                case EDIT_GRADE -> {
                    stage.setTitle("Modification de la note");
                    addGradeController.loadData(this, false);
                }
                case DELETE_GRADE -> {
                    loader = new FXMLLoader(MainWindow.class.getResource("delete-warning-window.fxml"));
                    root = loader.load();
                    DeleteWarningController deleteWarningController = loader.getController();
                    deleteWarningController.labelStudentName.setText("la note");
                    deleteWarningController.buttonConfirm.setOnAction(actionEvent -> {
                        deleteGrade();
                        deleteWarningController.buttonCancel.fire();
                    });
                }
            }
            stage.setScene(new Scene(root));
            return stage;
        }catch(IOException e){
            System.out.println("FXML file not found!");
            return new Stage();
        }
    }

    private void setupEvents(){
        this.buttonAddGrade.setOnAction(actionEvent -> setupGradeWindow(Action.ADD_GRADE).show());
        this.buttonEditGrade.setOnAction(actionEvent -> setupGradeWindow(Action.EDIT_GRADE).show());
        this.buttonDeleteGrade.setOnAction(actionEvent -> setupGradeWindow(Action.DELETE_GRADE).show());

        this.comboBoxCourse.valueProperty().addListener((observableValue, s, t1) -> {
            int index = this.comboBoxCourse.getSelectionModel().getSelectedIndex();
            this.setCourseInfo(selectedStudent.getCourseList().get(index));
            this.tableViewGrade.setItems(FXCollections.observableArrayList(this.selectedStudent.getCourseList().get(index).getGradeList()));
            this.selectedCourse = this.selectedStudent.getCourseList().get(index);
        });

        this.tableViewGrade.getSelectionModel().getSelectedCells()
                .addListener((ListChangeListener<? super TablePosition>) change -> setButtons(change.getList().isEmpty()));

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
        this.labelCourseAverage.setText((int)course.getAverageInPercent() + "%");
    }

    private void setupTableView(){
        this.tableColumnGrade.setCellValueFactory(new PropertyValueFactory<Grade, String>("Grade"));
        this.tableColumnGradePercent.setCellValueFactory(new PropertyValueFactory<Grade, String>("GradeInPercent"));
        ObservableList<Grade> gradeObservableList = FXCollections.observableArrayList();
        if(!this.selectedStudent.getCourseList().isEmpty())
            gradeObservableList.addAll(this.selectedStudent.getCourseList().get(0).getGradeList());
        else{
            this.buttonAddGrade.setDisable(true);
        }
        this.tableViewGrade.getItems().setAll(gradeObservableList);
    }

    private void setButtons(boolean disabled){
        this.buttonEditGrade.setDisable(disabled);
        this.buttonDeleteGrade.setDisable(disabled);
    }

    private void deleteGrade(){
        int index = Data.selectedStudent.getCourseList().indexOf(this.selectedCourse);
        Data.selectedStudent.getCourseList().get(index).getGradeList().remove(this.tableViewGrade.getSelectionModel().getSelectedItem());
        this.tableViewGrade.getItems().remove(this.tableViewGrade.getSelectionModel().getSelectedItem());
    }
}
