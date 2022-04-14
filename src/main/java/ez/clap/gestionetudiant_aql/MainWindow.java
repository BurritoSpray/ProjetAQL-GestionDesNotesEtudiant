package ez.clap.gestionetudiant_aql;

import ez.clap.gestionetudiant_aql.controllers.MainWindowController;
import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {
    private FXMLLoader fxmlLoader;
    private MainWindowController mainWindowController;
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        Data.loadDataFromFiles();
        fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        mainWindowController = fxmlLoader.getController();
        setupMainWindow();
        stage.setTitle("Gestionnaire d'étudiants");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        primaryStage = stage;
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Data.saveDataToFiles();
    }

    public static void main(String[] args) {
        launch();
    }

    public void setupMainWindow() {
        setupStudentTableView();
        setupCourseTableView();
        setupComboBox();
    }


    private void setupComboBox() {
        TabPane tabPane = mainWindowController.tabPaneMain;
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, newIndex) -> {
            if (newIndex.intValue() == 0) {
                mainWindowController.comboBoxSearchOption.getItems().clear();
                mainWindowController.comboBoxSearchOption.getItems().addAll("Numéro", "Nom-Prénom");
            } else {
                mainWindowController.comboBoxSearchOption.getItems().clear();
                mainWindowController.comboBoxSearchOption.getItems().addAll("Titre", "Code", "Numéro");
            }
            mainWindowController.comboBoxSearchOption.getSelectionModel().selectFirst();
            mainWindowController.textFieldSearch.clear();
        });
        tabPane.getSelectionModel().selectNext();
        tabPane.getSelectionModel().selectFirst();
    }


    private void setupCourseTableView() {
        TableView<Course> tableViewCourse = mainWindowController.tableViewCourse;

        mainWindowController.tableColumnCourseTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("Title"));
        mainWindowController.tableColumnCourseNumber.setCellValueFactory(new PropertyValueFactory<Course, String>("CourseNumber"));
        mainWindowController.tableColumnCourseCode.setCellValueFactory(new PropertyValueFactory<Course, String>("Code"));

        FilteredList<Course> filteredCourse = new FilteredList<>(Data.getCourseList(), p -> true);
        mainWindowController.textFieldSearch.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredCourse.setPredicate(course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (mainWindowController.comboBoxSearchOption.getItems().size() == 3) {
                    if (mainWindowController.comboBoxSearchOption.getSelectionModel().getSelectedIndex() == 0) {
                        return course.getTitle().toLowerCase().contains(lowerCaseFilter);
                    } else if (mainWindowController.comboBoxSearchOption.getSelectionModel().getSelectedIndex() == 1) {
                        return course.getCode().toLowerCase().contains(lowerCaseFilter);
                    } else {
                        return course.getCourseNumber().toLowerCase().contains(lowerCaseFilter);
                    }
                }
                return false;
            });
            if(filteredCourse.size() == 0){
                setCourseButtons(true);
            }else{
                tableViewCourse.getSelectionModel().selectFirst();
            }
        }));

        SortedList<Course> sortedCourse = new SortedList<>(filteredCourse);
        sortedCourse.comparatorProperty().bind(mainWindowController.tableViewCourse.comparatorProperty());
        mainWindowController.tableViewCourse.setItems(sortedCourse);

        tableViewCourse.setOnMouseClicked(event ->
                setCourseButtons(tableViewCourse.selectionModelProperty().get().isEmpty()));
        tableViewCourse.getItems().addListener((ListChangeListener<? super Course>) event ->
                setCourseButtons(tableViewCourse.getItems().size() == 0));
    }

    private void setupStudentTableView() {
        TableView<Student> tableViewStudent = mainWindowController.tableViewStudent;

        MainWindowController mainWindowController = fxmlLoader.getController();
        mainWindowController.tableColumnNumber.setCellValueFactory(new PropertyValueFactory<Student, String>("StudentID"));
        mainWindowController.tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("FirstName"));
        mainWindowController.tableColumnSecondName.setCellValueFactory(new PropertyValueFactory<Student, String>("SecondName"));
        mainWindowController.tableColumnCourse.setCellValueFactory(new PropertyValueFactory<Student, ComboBox<String>>("CourseListAsComboBox"));

        FilteredList<Student> filteredStudent = new FilteredList<>(Data.getStudentList(), p -> true);
        mainWindowController.textFieldSearch.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredStudent.setPredicate(student -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (mainWindowController.comboBoxSearchOption.getSelectionModel().getSelectedIndex() == 0) {
                    return student.getStudentID().toLowerCase().contains(lowerCaseFilter);
                } else {
                    if (student.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else
                        return student.getSecondName().toLowerCase().contains(lowerCaseFilter);
                }
            });
            if(filteredStudent.size() == 0){
                setStudentButtons(0);
            }else{
                tableViewStudent.getSelectionModel().selectFirst();

            }
        }));

        SortedList<Student> sortedStudent = new SortedList<>(filteredStudent);
        sortedStudent.comparatorProperty().bind(tableViewStudent.comparatorProperty());
        tableViewStudent.setItems(sortedStudent);

        // Event sur un click
//        tableViewStudent.setOnMouseClicked(event ->
//                setStudentButtons(tableViewStudent.selectionModelProperty().get().isEmpty()));

        // Event quand la liste change
        tableViewStudent.getItems().addListener((ListChangeListener<? super Student>) event ->
                setStudentButtons(tableViewStudent.getItems().size()));

        tableViewStudent.getSelectionModel().selectedItemProperty().addListener((observablevalue, oldtab, newtab) -> {
            setStudentButtons(tableViewStudent.getSelectionModel().getSelectedItems().size());
        });

        tableViewStudent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // TODO: Glitch quand on fais une selection multiple et click sur le dernier element

    }





    private void setStudentButtons(int shitCount) {
        if(shitCount > 1){
            mainWindowController.buttonEditStudent.setDisable(true);
            mainWindowController.buttonDeleteStudent.setDisable(false);
        }else if(shitCount == 1){
            mainWindowController.buttonEditStudent.setDisable(false);
            mainWindowController.buttonDeleteStudent.setDisable(false);
        }else{
            mainWindowController.buttonEditStudent.setDisable(true);
            mainWindowController.buttonDeleteStudent.setDisable(true);
        }
    }
/*
    private void setMultipleStudentButtons(boolean disabled) {
        mainWindowController.buttonEditStudent.disableProperty().set(disabled);
    }
*/
    private void setCourseButtons(boolean disabled) {
        mainWindowController.buttonDeleteCourse.disableProperty().set(disabled);
        mainWindowController.buttonEditCourse.disableProperty().set(disabled);
    }

}