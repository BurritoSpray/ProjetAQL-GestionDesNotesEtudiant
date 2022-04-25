package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.MainWindow;
import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Grade;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindowController {

    enum Action{
        CREATE_STUDENT,
        EDIT_STUDENT,
        DELETE_STUDENT,
        SHOW_GRADES,
        CREATE_COURSE,
        EDIT_COURSE,
        DELETE_COURSE
    }

    @FXML
    public Button buttonAddStudent, buttonEditStudent, buttonDeleteStudent,
            buttonAddCourse, buttonEditCourse, buttonDeleteCourse,
            buttonShowGrade, buttonExport;
    @FXML
    public TableView<Student> tableViewStudent;
    @FXML
    public TableView<Course> tableViewCourse;
    @FXML
    public TableColumn<Student,String> tableColumnFirstName, tableColumnSecondName, tableColumnNumber;
    @FXML
    public TableColumn<Student, ComboBox<String>> tableColumnCourse;
    @FXML
    public TableColumn<Course,String> tableColumnCourseTitle,tableColumnCourseNumber,tableColumnCourseCode;
    @FXML
    public TextField textFieldSearch;
    @FXML
    public ComboBox<String> comboBoxSearchOption;
    @FXML
    public TabPane tabPaneMain;


    private Stage setupStage(Action action, String resource) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = getLoaderFromResources(resource);
        assert fxmlLoader != null;
        Parent root = fxmlLoader.getRoot();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        switch (action) {
            case EDIT_STUDENT -> {
                stage.setTitle("Modifier l'étudiant");
                ManageStudentController manageStudentController = fxmlLoader.getController();
                manageStudentController.loadData(this, false);
                manageStudentController.buttonConfirm.setText("Modifier");
            }
            case CREATE_STUDENT -> {
                stage.setTitle("Creer un étudiant");
                ManageStudentController manageStudentController = fxmlLoader.getController();
                manageStudentController.loadData(this, true);
            }
            // TODO: Creer une fonction pour enlever la repetition des DELETE case
            case DELETE_STUDENT -> {
                stage.setTitle("Attention!");
                DeleteWarningController deleteWarningController = fxmlLoader.getController();
                int selectionCount = this.tableViewStudent.getSelectionModel().getSelectedItems().size();
                if(selectionCount > 1){
                    deleteWarningController.labelStudentName.setText(selectionCount + " Étudiants");
                }else{
                    Student selectedStudent = this.tableViewStudent.getSelectionModel().getSelectedItem();
                    deleteWarningController.labelStudentName.setText(selectedStudent.getFirstName() + " " + selectedStudent.getSecondName());
                }
                deleteWarningController.buttonConfirm.setOnAction(event -> {
                    Data.getStudentList().removeAll(this.tableViewStudent.getSelectionModel().getSelectedItems());
                    closeWindow(deleteWarningController.buttonConfirm);
                });
            }
            case CREATE_COURSE -> {
                stage.setTitle("Creer un cours");
                ManageCourseController manageCourseController = fxmlLoader.getController();
                manageCourseController.loadCourse(this, true);
                manageCourseController.buttonConfirm.setText("Confirmer");
            }
            case EDIT_COURSE -> {
                stage.setTitle("Modifier le cours");
                ManageCourseController manageCourseController = fxmlLoader.getController();
                manageCourseController.loadCourse(this, false);
                manageCourseController.buttonConfirm.setText("Modifier");
            }
            case DELETE_COURSE -> {
                stage.setTitle("Attention!");
                DeleteWarningController deleteCourseController = fxmlLoader.getController();
                int selectionCount = this.tableViewCourse.getSelectionModel().getSelectedItems().size();
                if(selectionCount > 1){
                    deleteCourseController.labelStudentName.setText(selectionCount + " Cours");

                }else{
                    Course selectedCourse = this.tableViewCourse.getSelectionModel().getSelectedItem();
                    deleteCourseController.labelStudentName.setText(selectedCourse.getTitle() + " " + selectedCourse.getCourseNumber());
                }
                deleteCourseController.buttonConfirm.setOnAction(event -> {
                    Data.getCourseList().removeAll(this.tableViewCourse.getSelectionModel().getSelectedItems());
                    closeWindow(deleteCourseController.buttonConfirm);

                });
            }
            case SHOW_GRADES -> {
                ManageGradeController manageGradeController = fxmlLoader.getController();
                Student selectedStudent = this.tableViewStudent.getSelectionModel().getSelectedItem();
                stage.setTitle("Notes de " + selectedStudent.getStudentID() + "(" + selectedStudent.getFirstName() + " " + selectedStudent.getSecondName()+ ")");
                manageGradeController.loadData(this);

            }
        }

        stage.setScene(new Scene(root));
        return stage;
    }


    public void showWarningPopup(String title, String warningMessage, String buttonText){
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setContentText(warningMessage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType closeButton = new ButtonType(buttonText, ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.show();
    }

    private FXMLLoader getLoaderFromResources(String resource){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(resource));
            fxmlLoader.load();
            return fxmlLoader;
        }catch(IOException e){
            System.out.println("Verifier si " + resource + " existe dans le repertoire resources du projet!!!");
        }
        return null;
    }

    private void closeWindow(Control control){
        ((Stage)control.getScene().getWindow()).close();
    }

    public void loadWindow(){
        setupStudentTableView();
        setupCourseTableView();
        setupComboBox();
        setupEvents();
    }

    private void setupEvents(){
        this.buttonAddStudent.setOnAction(event -> setupStage(Action.CREATE_STUDENT, "manage-student-window.fxml").show());
        this.buttonEditStudent.setOnAction(event -> setupStage(Action.EDIT_STUDENT, "manage-student-window.fxml").show());
        this.buttonDeleteStudent.setOnAction(event -> setupStage(Action.DELETE_STUDENT, "delete-warning-window.fxml").show());
        this.buttonShowGrade.setOnAction(event -> setupStage(Action.SHOW_GRADES, "manage-grade-window.fxml").show());
        this.buttonAddCourse.setOnAction(event -> setupStage(Action.CREATE_COURSE, "manage-course-window.fxml").show());
        this.buttonEditCourse.setOnAction(event -> setupStage(Action.EDIT_COURSE, "manage-course-window.fxml").show());
        this.buttonDeleteCourse.setOnAction(event -> setupStage(Action.DELETE_COURSE, "delete-warning-window.fxml").show());
        this.buttonExport.setOnAction(actionEvent -> exportSelectedStudents());

        this.tableViewStudent.setOnKeyTyped(keyEvent -> {
            if(keyEvent.getCharacter().equals("\u007F") && this.tableViewStudent.isFocused() && !this.tableViewStudent.getSelectionModel().isEmpty())
                this.buttonDeleteStudent.fire();
        });

        this.tableViewStudent.getItems().addListener((ListChangeListener<? super Student>) c -> {
            c.next();
            if(this.tableViewStudent.getItems().size() > 0)
                this.tableViewStudent.getSelectionModel().clearAndSelect(c.getFrom());
        });

        this.tableViewStudent.setOnMouseClicked(event ->
                setStudentButtons(this.tableViewStudent.selectionModelProperty().get().isEmpty()));

        this.tableViewStudent.getItems().addListener((ListChangeListener<? super Student>) event ->{
                    setStudentButtons(this.tableViewStudent.getItems().size() == 0);
                    Data.selectedStudent = this.tableViewStudent.getSelectionModel().getSelectedItem();
                });

        this.tableViewCourse.setOnKeyTyped(keyEvent -> {
            if(keyEvent.getCharacter().equals("\u007F") && this.tableViewCourse.isFocused() && !this.tableViewCourse.getSelectionModel().isEmpty()) {
                this.buttonDeleteCourse.fire();
            }
        });

        this.tableViewCourse.getSelectionModel().getSelectedCells()
                .addListener((ListChangeListener<? super TablePosition>) change -> setCourseButtons(change.getList().isEmpty()));

        this.tableViewCourse.setOnMouseClicked(event ->
                setCourseButtons(this.tableViewCourse.selectionModelProperty().get().isEmpty()));

        this.tableViewCourse.getItems().addListener((ListChangeListener<? super Course>) event ->
                setCourseButtons(this.tableViewCourse.getItems().size() == 0));

        this.tableViewCourse.getItems().addListener((ListChangeListener<? super Course>) c -> {
            c.next();
            if(this.tableViewCourse.getItems().size() > 0){
                this.tableViewCourse.getSelectionModel().clearAndSelect(c.getFrom());
            }
        });

        this.comboBoxSearchOption.setOnAction(event -> {
            String searchInput = this.textFieldSearch.getText();
            this.textFieldSearch.textProperty().set(searchInput + " ");
            this.textFieldSearch.textProperty().set(searchInput);
        });

        this.tabPaneMain.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != newValue){
                this.tableViewStudent.getSelectionModel().selectFirst();
                this.tableViewCourse.getSelectionModel().selectFirst();
            }
        });
    }

    private void setupComboBox() {
        tabPaneMain.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, newIndex) -> {
            if (newIndex.intValue() == 0) {
                this.comboBoxSearchOption.getItems().clear();
                this.comboBoxSearchOption.getItems().addAll("Numéro", "Nom-Prénom");
            } else {
                this.comboBoxSearchOption.getItems().clear();
                this.comboBoxSearchOption.getItems().addAll("Titre", "Code", "Numéro");
            }
            this.comboBoxSearchOption.getSelectionModel().selectFirst();
            this.textFieldSearch.clear();
        });
        this.tabPaneMain.getSelectionModel().selectNext();
        this.tabPaneMain.getSelectionModel().selectFirst();
    }

    private void setupStudentTableView() {
        this.tableViewStudent.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
        this.tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
        this.tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        this.tableColumnSecondName.setCellValueFactory(new PropertyValueFactory<>("SecondName"));
        this.tableColumnCourse.setCellValueFactory(new PropertyValueFactory<>("CourseListAsComboBox"));

        FilteredList<Student> filteredStudent = new FilteredList<>(Data.getStudentList(), p -> true);
        this.textFieldSearch.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredStudent.setPredicate(student -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (this.comboBoxSearchOption.getSelectionModel().getSelectedIndex() == 0) {
                    return student.getStudentID().toLowerCase().contains(lowerCaseFilter);
                } else {
                    if (student.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else
                        return student.getSecondName().toLowerCase().contains(lowerCaseFilter);
                }
            });
            if(filteredStudent.size() == 0){
                setStudentButtons(true);
            }else{
                this.tableViewStudent.getSelectionModel().selectFirst();
            }
        }));

        SortedList<Student> sortedStudent = new SortedList<>(filteredStudent);
        sortedStudent.comparatorProperty().bind(this.tableViewStudent.comparatorProperty());
        this.tableViewStudent.setItems(sortedStudent);
    }

    private void setupCourseTableView() {
        this.tableViewCourse.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
        this.tableColumnCourseTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        this.tableColumnCourseNumber.setCellValueFactory(new PropertyValueFactory<>("CourseNumber"));
        this.tableColumnCourseCode.setCellValueFactory(new PropertyValueFactory<>("Code"));

        FilteredList<Course> filteredCourse = new FilteredList<>(Data.getCourseList(), p -> true);
        this.textFieldSearch.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredCourse.setPredicate(course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (this.comboBoxSearchOption.getItems().size() == 3) {
                    if (this.comboBoxSearchOption.getSelectionModel().getSelectedIndex() == 0) {
                        return course.getTitle().toLowerCase().contains(lowerCaseFilter);
                    } else if (this.comboBoxSearchOption.getSelectionModel().getSelectedIndex() == 1) {
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
                this.tableViewCourse.getSelectionModel().selectFirst();
            }
        }));

        SortedList<Course> sortedCourse = new SortedList<>(filteredCourse);
        sortedCourse.comparatorProperty().bind(this.tableViewCourse.comparatorProperty());
        this.tableViewCourse.setItems(sortedCourse);
    }

    private void setStudentButtons(boolean disabled) {
        this.buttonDeleteStudent.setDisable(disabled);
        this.buttonEditStudent.setDisable(disabled);
        this.buttonShowGrade.setDisable(disabled);
        this.buttonExport.setDisable(disabled);
    }

    private void setCourseButtons(boolean disabled) {
        this.buttonDeleteCourse.disableProperty().set(disabled);
        this.buttonEditCourse.disableProperty().set(disabled);
    }

    private void exportSelectedStudents(){
        File outputFolder = new File(Data.getDataFolder().getPath() + "/output/");
        if(!outputFolder.exists())
            outputFolder.mkdir();
        ObservableList<Student> studentsToExport = this.tableViewStudent.getSelectionModel().getSelectedItems();
        try {
            for (Student student : studentsToExport) {
                ArrayList<String> data = new ArrayList<>();
                data.add(String.format("""
                        Numéros étudiant: %s
                        Nom: %s
                        Prénom: %s
                        Cours:
                        
                        """, student.getStudentID(), student.getSecondName(), student.getFirstName()));

                for(Course course : student.getCourseList()){
                    data.add(String.format("""
                            Titre: %s
                            Code: %s
                            Numéros de cours: %s
                            """, course.getTitle(), course.getCode(), course.getCourseNumber()));
                    for(Grade grade : course.getGradeList()){
                        data.add(String.format("""
                                Note: %s | %c
                                """, grade.getGradeInPercentString(), grade.getGrade()));
                    }
                    data.add(String.format("Moyenne: %s\n\n", course.getAverageInPercent()+"%"));
                }
                FileWriter output = new FileWriter(outputFolder.getPath() + "/" + student.getStudentID() + ".txt", false);
                for(String string : data){
                    output.write(string);
                }
                output.close();
            }
            showWarningPopup("Opération terminé", "Les fichier se trouve dans /Data/output/", "OK");
        }catch(IOException e){
            this.showWarningPopup("Erreur", "Une erreur est survenue lors de l'écriture!", "OK");
            System.out.println(e.getMessage());
        }
    }

}