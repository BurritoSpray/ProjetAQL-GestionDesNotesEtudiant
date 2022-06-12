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

    public Course selectedCourse;

    public void loadData(MainWindowController mainWindowController){
        setupEvents();
        setStudentInfo(Data.selectedStudent);
        this.comboBoxCourse.getSelectionModel().selectFirst();
        setCourseInfo(Data.selectedStudent.getCourseList().isEmpty() ? new Course() : Data.selectedStudent.getCourseList().get(0));
        setupTableView();
    }

    private Stage setupGradeWindow(Action action){
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("add-grade-window.fxml"));
            Parent root = fxmlLoader.load();
            AddGradeController addGradeController = fxmlLoader.getController();

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
                    fxmlLoader = new FXMLLoader(MainWindow.class.getResource("delete-warning-window.fxml"));
                    root = fxmlLoader.load();
                    DeleteWarningController deleteWarningController = fxmlLoader.getController();
                    deleteWarningController.labelStudentName.setText("la note");
                    deleteWarningController.buttonConfirm.setOnAction(actionEvent -> {
                        deleteGrade();
                        deleteWarningController.buttonCancel.fire();
                    });
                }
            }
            stage.setScene(new Scene(root));
            return stage;
        }catch(IOException exception){
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
            this.setCourseInfo(Data.selectedStudent.getCourseList().get(index));
            this.tableViewGrade.setItems(FXCollections.observableArrayList(Data.selectedStudent.getCourseList().get(index).getGradeList()));
            this.selectedCourse = Data.selectedStudent.getCourseList().get(index);
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
        this.tableColumnGrade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
        this.tableColumnGradePercent.setCellValueFactory(new PropertyValueFactory<>("GradeInPercent"));
        ObservableList<Grade> gradeObservableList = FXCollections.observableArrayList();
        if(!Data.selectedStudent.getCourseList().isEmpty())
            gradeObservableList.addAll(Data.selectedStudent.getCourseList().get(0).getGradeList());
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
