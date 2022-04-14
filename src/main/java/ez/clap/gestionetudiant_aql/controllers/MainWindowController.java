package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.MainWindow;
import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.collections.ListChangeListener;
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

import java.io.IOException;

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
    public Button buttonAddStudent, buttonEditStudent, buttonDeleteStudent, buttonAddCourse, buttonEditCourse, buttonDeleteCourse, buttonShowGrade;
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

    @FXML
    private void onButtonAddStudentClick() {
        Stage stage = setupStudentStage(Action.CREATE_STUDENT, "manage-student-window.fxml");
        stage.show();
    }


    @FXML
    private void onButtonEditStudentClick() {
        Stage stage = setupStudentStage(Action.EDIT_STUDENT, "manage-student-window.fxml");
        stage.show();
    }


    @FXML
    private void onButtonDeleteStudentClick() {
        Stage stage = setupStudentStage(Action.DELETE_STUDENT, "delete-warning-window.fxml");
        stage.show();
    }

    @FXML
    private void onButtonAddCourseClick() {
        Stage stage = setupCourseStage(Action.CREATE_COURSE, "manage-course-window.fxml");
        stage.show();
    }

    @FXML
    private void onButtonEditCourseClick() {
        Stage stage = setupCourseStage(Action.EDIT_COURSE, "manage-course-window.fxml");
        stage.show();
    }

    @FXML
    private void onButtonDeleteCourseClick() {
        Stage stage = setupCourseStage(Action.DELETE_COURSE, "delete-warning-window.fxml");
        stage.show();
    }

    @FXML
    private void onButtonShowGradeClick(){
        Stage stage = setupStudentStage(Action.SHOW_GRADES, "manage-grade-window.fxml");
        stage.show();
    }

    private Stage setupStudentStage(Action action, String resource) {
        Stage stage = new Stage();
        FXMLLoader loader = getLoaderFromResources(resource);
        assert loader != null;
        Parent root = loader.getRoot();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // TODO: Ajouter dans le CourseListView la liste de tous les cours disponible et implementer dans l'objet etudiant seulement eu qui son cocher

        switch (action) {
            case EDIT_STUDENT -> {
                stage.setTitle("Modifier l'étudiant");
                ManageStudentController manageStudentController = loader.getController();
                manageStudentController.loadStudent(tableViewStudent.getSelectionModel().getSelectedItem());
                manageStudentController.buttonConfirm.setText("Modifier");
                manageStudentController.buttonConfirm.setOnAction(event -> {
                    if (isStudentFieldsValid(manageStudentController)) {
                        removeSelectedStudent();
                        addStudentFromManageStudentController(manageStudentController);
                        closeWindow(manageStudentController.buttonConfirm);
                    } else {
                        showWarningPopup("Erreur", "Information manquante", "OK");
                    }
                });
            }
            case CREATE_STUDENT -> {
                stage.setTitle("Creer un étudiant");
                ManageStudentController manageStudentController = loader.getController();

                manageStudentController.buttonConfirm.setOnAction(event -> {
                    if (isStudentFieldsValid(manageStudentController)) {
                        addStudentFromManageStudentController(manageStudentController);

                        closeWindow(manageStudentController.buttonConfirm);
                    } else {
                        showWarningPopup("Erreur", "Information manquante!", "OK");
                    }
                });
            }
            case DELETE_STUDENT -> {
                stage.setTitle("Attention!");
                DeleteWarningController deleteWarningController = loader.getController();
                Student selectedStudent = tableViewStudent.getSelectionModel().getSelectedItem();
                deleteWarningController.labelStudentName.setText(selectedStudent.getFirstName() + " " + selectedStudent.getSecondName());

                deleteWarningController.buttonConfirm.setOnAction(event -> {
                    Data.getStudentList().remove(selectedStudent);
//                    tableViewStudent.getItems().remove(selectedStudent);
                    closeWindow(deleteWarningController.buttonConfirm);
                });
            }
            case SHOW_GRADES -> {
                ManageGradeController manageGradeController = loader.getController();
                Student selectedStudent = this.tableViewStudent.getSelectionModel().getSelectedItem();
                stage.setTitle("Notes de " + selectedStudent.getStudentID());
                manageGradeController.loadData(selectedStudent);


            }
        }

        stage.setScene(new Scene(root));
        return stage;
    }

    private Stage setupCourseStage (Action action, String resource) {
        Stage stage = new Stage();
        FXMLLoader loader = getLoaderFromResources(resource);
        assert loader != null;
        Parent root = loader.getRoot();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        switch (action){
            case CREATE_COURSE:{
                stage.setTitle("Creer un cours");
                ManageCourseController manageCourseController = loader.getController();

                manageCourseController.buttonConfirm.setOnAction(event ->{
                    if(isCourseFieldsValid(manageCourseController)){
                        addCourseFromManageCourseController(manageCourseController);
                        closeWindow(manageCourseController.buttonConfirm);
                    }
                    else{
                        showWarningPopup("Erreur", "Information manquante","OK");
                    }
                });
                break;
            }
            case EDIT_COURSE:{
                stage.setTitle("Modifier le cours");
                ManageCourseController manageCourseController = loader.getController();
                manageCourseController.loadCourse(tableViewCourse.getSelectionModel().getSelectedItem());
                manageCourseController.buttonConfirm.setText("Modifier");
                manageCourseController.buttonConfirm.setOnAction(event -> {
                    if(isCourseFieldsValid(manageCourseController)){
                        removeSelectedCourse();
                        addCourseFromManageCourseController(manageCourseController);
                        closeWindow(manageCourseController.buttonConfirm);
                    }
                    else{
                        showWarningPopup("Erreur", "Information manquante", "OK");
                    }
                });
                break;
            }
            // TODO: vérifier le nom du controller et des attributs
            case DELETE_COURSE:{
                stage.setTitle("Attention!");
                DeleteWarningController deleteCourseController = loader.getController();
                Course selectedCourse = tableViewCourse.getSelectionModel().getSelectedItem();
                deleteCourseController.labelStudentName.setText(selectedCourse.getTitle() + " " + selectedCourse.getCourseNumber());

                deleteCourseController.buttonConfirm.setOnAction(event -> {
                    Data.getCourseList().remove(selectedCourse);
                    closeWindow(deleteCourseController.buttonConfirm);
                });
                break;
            }
        }

        stage.setScene(new Scene(root));
        return stage;
    }


    private boolean isStudentFieldsValid(ManageStudentController manageStudentController){
        return !manageStudentController.textFieldFirstName.getText().isEmpty() &&
                !manageStudentController.textFieldSecondName.getText().isEmpty() &&
                !manageStudentController.textFieldNumber.getText().isEmpty();
    }

    private void showWarningPopup(String title, String warningMessage, String buttonText){
        Dialog dialog = new Dialog();
        dialog.setTitle(title);
        dialog.setContentText(warningMessage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType closeButton = new ButtonType(buttonText, ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.show();
    }

    private void addStudentFromManageStudentController(ManageStudentController manageStudentController){
        Student student = new Student(manageStudentController.textFieldFirstName.getText(),
                manageStudentController.textFieldSecondName.getText(),
                manageStudentController.textFieldNumber.getText());
        Data.getStudentList().add(student);
//        tableViewStudent.getItems().add(student);
    }

    private void removeSelectedStudent(){
        Student selectedStudent = tableViewStudent.getSelectionModel().getSelectedItem();
        Data.getStudentList().remove(selectedStudent);
//        tableViewStudent.getItems().remove(selectedStudent);
    }

    // TODO: enlever la répétition
    private  boolean isCourseFieldsValid(ManageCourseController manageCourseController){
        return !manageCourseController.textFieldNumber.getText().isEmpty() &&
                !manageCourseController.textFieldCode.getText().isEmpty() &&
                !manageCourseController.textFieldTitle.getText().isEmpty();
    }

    private  void addCourseFromManageCourseController(ManageCourseController manageCourseController){
        Course course = new Course(manageCourseController.textFieldTitle.getText(),
                manageCourseController.textFieldCode.getText(),
                manageCourseController.textFieldNumber.getText());
        Data.getCourseList().add(course);
        //tableViewCourse.getItems().addAll(Data.getCourseList());
    }

    private void removeSelectedCourse(){
        Course selectedCourse = tableViewCourse.getSelectionModel().getSelectedItem();
        Data.getCourseList().remove(selectedCourse);
//        tableViewCourse.getItems().remove(selectedCourse);
    }

    private FXMLLoader getLoaderFromResources(String resource){
        try{
            FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource(resource));
            loader.load();
            return loader;
        }catch(IOException e){
            System.out.println("Verifier si " + resource + " existe dans le repertoire resources du projet!!!");
        }
        return null;
    }

    private void closeWindow(Control control){
        ((Stage)control.getScene().getWindow()).close();
    }

    public void loadWindow(){
        this.buttonShowGrade.setDisable(false);
        setupStudentTableView();
        setupCourseTableView();
        setupComboBox();
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
        tabPaneMain.getSelectionModel().selectNext();
        tabPaneMain.getSelectionModel().selectFirst();
    }

    private void setupStudentTableView() {
        this.tableColumnNumber.setCellValueFactory(new PropertyValueFactory<Student, String>("StudentID"));
        this.tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("FirstName"));
        this.tableColumnSecondName.setCellValueFactory(new PropertyValueFactory<Student, String>("SecondName"));
        this.tableColumnCourse.setCellValueFactory(new PropertyValueFactory<Student, ComboBox<String>>("CourseListAsComboBox"));

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
                tableViewStudent.getSelectionModel().selectFirst();
            }
        }));

        SortedList<Student> sortedStudent = new SortedList<>(filteredStudent);
        sortedStudent.comparatorProperty().bind(tableViewStudent.comparatorProperty());
        tableViewStudent.setItems(sortedStudent);

        tableViewStudent.setOnMouseClicked(event ->
                setStudentButtons(tableViewStudent.selectionModelProperty().get().isEmpty()));

        tableViewStudent.getItems().addListener((ListChangeListener<? super Student>) event ->
                setStudentButtons(tableViewStudent.getItems().size() == 0));
    }

    private void setupCourseTableView() {
        this.tableColumnCourseTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("Title"));
        this.tableColumnCourseNumber.setCellValueFactory(new PropertyValueFactory<Course, String>("CourseNumber"));
        this.tableColumnCourseCode.setCellValueFactory(new PropertyValueFactory<Course, String>("Code"));

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
                tableViewCourse.getSelectionModel().selectFirst();
            }
        }));

        SortedList<Course> sortedCourse = new SortedList<>(filteredCourse);
        sortedCourse.comparatorProperty().bind(this.tableViewCourse.comparatorProperty());
        this.tableViewCourse.setItems(sortedCourse);

        tableViewCourse.setOnMouseClicked(event ->
                setCourseButtons(tableViewCourse.selectionModelProperty().get().isEmpty()));
        tableViewCourse.getItems().addListener((ListChangeListener<? super Course>) event ->
                setCourseButtons(tableViewCourse.getItems().size() == 0));
    }

    private void setStudentButtons(boolean disabled) {
        this.buttonDeleteStudent.disableProperty().set(disabled);
        this.buttonEditStudent.disableProperty().set(disabled);
    }

    private void setCourseButtons(boolean disabled) {
        this.buttonDeleteCourse.disableProperty().set(disabled);
        this.buttonEditCourse.disableProperty().set(disabled);
    }

}