package ez.clap.gestionetudiant_aql;

import ez.clap.gestionetudiant_aql.controllers.MainWindowController;
import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainWindow extends Application {
    private FXMLLoader fxmlLoader;
    private MainWindowController mainWindowController;
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        loadTestData();
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

    public static void main(String[] args) {
        launch();
    }

    public void setupMainWindow() throws IOException {
        setupStudentTableView();
        setupCourseTableView();
    }

    private void setupCourseTableView() {
        TableView<Course> tableViewCourse = mainWindowController.tableViewCourse;
        ObservableList<Course> courseData = FXCollections.observableArrayList();
        courseData.addAll(Data.getCourseList());

        mainWindowController.tableColumnCourseTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("Title"));
        mainWindowController.tableColumnCourseNumber.setCellValueFactory(new PropertyValueFactory<Course, String>("CourseNumber"));
        mainWindowController.tableColumnCourseCode.setCellValueFactory(new PropertyValueFactory<Course, String>("Code"));
//        tableViewCourse.getItems().addAll(Data.getCourseList());

        FilteredList<Course> filteredCourse = new FilteredList<>(courseData, p -> true);
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
        ObservableList<Student> studentData = FXCollections.observableArrayList();
        studentData.addAll(Data.getStudentList());

        MainWindowController mainWindowController = fxmlLoader.getController();
        mainWindowController.tableColumnNumber.setCellValueFactory(new PropertyValueFactory<Student, String>("StudentID"));
        mainWindowController.tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("FirstName"));
        mainWindowController.tableColumnSecondName.setCellValueFactory(new PropertyValueFactory<Student, String>("SecondName"));
        mainWindowController.tableColumnCourse.setCellValueFactory(new PropertyValueFactory<Student, ComboBox<String>>("CourseListAsComboBox"));

        FilteredList<Student> filteredStudent = new FilteredList<>(studentData, p -> true);
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
        }));

        SortedList<Student> sortedStudent = new SortedList<>(filteredStudent);
        sortedStudent.comparatorProperty().bind(tableViewStudent.comparatorProperty());
        tableViewStudent.setItems(sortedStudent);


//        tableViewStudent.getItems().addAll(Data.getStudentList());

        tableViewStudent.setOnMouseClicked(event ->
                setStudentButtons(tableViewStudent.selectionModelProperty().get().isEmpty()));

        tableViewStudent.getItems().addListener((ListChangeListener<? super Student>) event ->
                setStudentButtons(tableViewStudent.getItems().size() == 0));
    }

    private void setStudentButtons(boolean disabled) {
        mainWindowController.buttonDeleteStudent.disableProperty().set(disabled);
        mainWindowController.buttonEditStudent.disableProperty().set(disabled);
    }

    private void setCourseButtons(boolean disabled) {
        mainWindowController.buttonDeleteCourse.disableProperty().set(disabled);
        mainWindowController.buttonEditCourse.disableProperty().set(disabled);
    }


    // TODO: Remove this when Data class will be completed
    private void loadTestData() {
        Student s = new Student("asdas", "dsfsf", "23434");
        Student s2 = new Student("a34324", "dsfsf", "2543174");
        Student s3 = new Student("as4sfdsgs", "dfdshvxvcvf", "2353657");
        Student s4 = new Student("asdddccgrs", "daaaa", "234399874");
        Student s5 = new Student("Test", "test3342", "dsger", new ArrayList<>(Arrays.asList(new Course("MonCours", "12345", "54321"),
                new Course("MonCours2", "42314", "12345633"))));
        ArrayList<Student> students = new ArrayList<>(Arrays.asList(s, s2, s3, s4, s5));
        Data.setStudentList(students);

        Course c = new Course("asfsd", "D654Sfds", "d3fsa2434f");
        Course c1 = new Course("a657sfsd", "DS564fds", "df4s4342af");
        Course c2 = new Course("a1343sfsd", "676DSfds", "243dfsaf");
        Course c3 = new Course("as4234fsd", "gjjgDSfds", "d23fsaf");
        ArrayList<Course> courses = new ArrayList<>(Arrays.asList(c,c1,c2,c3));
        Data.setCourseList(courses);
    }
}