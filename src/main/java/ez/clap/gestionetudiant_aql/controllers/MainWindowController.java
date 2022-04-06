package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.MainWindow;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {

    enum Action{
        CREATE_STUDENT,
        EDIT_STUDENT,
        DELETE_STUDENT,
        SHOW_GRADES
    }

    @FXML
    public Button buttonAddStudent, buttonEditStudent, buttonDeleteStudent, buttonShowGrade;
    @FXML
    public TableView<Student> tableViewStudent;
    @FXML
    public TableColumn<Student,String> tableColumnFirstName, tableColumnSecondName, tableColumnNumber;
    @FXML
    public TableColumn<Student, ComboBox<String>> tableColumnCourse;
    @FXML
    public TabPane tabPaneMain;


    @FXML
    private void onButtonAddStudentClick() throws IOException {
        Stage stage = setupStudentStage(Action.CREATE_STUDENT, "manage-student-window.fxml");
        stage.show();
    }


    @FXML
    private void onButtonEditStudentClick() throws IOException {
        Stage stage = setupStudentStage(Action.EDIT_STUDENT, "manage-student-window.fxml");
        stage.show();
    }

    @FXML
    private void onButtonDeleteStudentClick() throws IOException {
        Stage stage = setupStudentStage(Action.DELETE_STUDENT, "delete-student-window.fxml");
        stage.showAndWait();
        tableViewStudent.getFocusModel().focusNext();
    }

    @FXML
    private void onButtonShowGrades() throws IOException {
        Stage stage = setupStudentStage(Action.SHOW_GRADES, "manage-grade-window.fxml");
        stage.show();
    }


    private Stage setupStudentStage(Action action, String resourceName) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource(resourceName));
        Parent root = loader.load();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // TODO: Ajouter dans le CourseListView la liste de tous les cours disponible et implementer dans l'objet etudiant seulement eu qui son cocher

        switch(action){
            // switch pour setup la fenetre
            case EDIT_STUDENT:{
                stage.setTitle("Modifier l'étudiant");
                ManageStudentController manageStudentController = loader.getController();
                manageStudentController.loadStudent(tableViewStudent.getSelectionModel().getSelectedItem());
                manageStudentController.buttonConfirm.setText("Modifier");
                manageStudentController.buttonConfirm.setOnAction(event -> {
                    if(isStudentFieldsValid(manageStudentController)){
                        removeSelectedStudent(manageStudentController);
                        addStudentFromManageStudentController(manageStudentController);
                        closeWindow(manageStudentController.buttonConfirm);
                    }else{
                        showWarningPopup("Erreur", "Information manquante", "OK");
                    }
                });
                break;
            }
            case CREATE_STUDENT:{
                stage.setTitle("Creer un étudiant");
                ManageStudentController manageStudentController = loader.getController();

                manageStudentController.buttonConfirm.setOnAction(event -> {
                    if(isStudentFieldsValid(manageStudentController)){
                        addStudentFromManageStudentController(manageStudentController);

                        closeWindow(manageStudentController.buttonConfirm);
                    }else {
                        showWarningPopup("Erreur", "Information manquante!", "OK");
                    }
                });
                break;
            }
            case DELETE_STUDENT:{
                stage.setTitle("Attention!");
                DeleteStudentController deleteStudentController = loader.getController();
                Student selectedStudent = tableViewStudent.getSelectionModel().getSelectedItem();
                deleteStudentController.labelStudentName.setText(selectedStudent.getFirstName() + " " + selectedStudent.getSecondName());

                deleteStudentController.buttonConfirm.setOnAction(event->{
                    Data.getStudentList().remove(selectedStudent);
                    tableViewStudent.getItems().remove(selectedStudent);
                    closeWindow(deleteStudentController.buttonConfirm);
                });
                break;
            }
            case SHOW_GRADES:{
                stage.setTitle("Gestionnaire");
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
        tableViewStudent.getItems().setAll(Data.getStudentList());
    }

    private void removeSelectedStudent(ManageStudentController manageStudentController){
        Student selectedStudent = tableViewStudent.getSelectionModel().getSelectedItem();
        Data.getStudentList().remove(selectedStudent);
        tableViewStudent.getItems().remove(selectedStudent);
    }

    private void closeWindow(Control control){
        ((Stage)control.getScene().getWindow()).close();
    }


}