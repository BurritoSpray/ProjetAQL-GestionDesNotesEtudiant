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
        Create,
        Edit,
        Delete
    }

    @FXML
    public Button buttonAddStudent, buttonEditStudent, buttonDeleteStudent;
    @FXML
    public TableView<Student> tableViewStudent;
    @FXML
    public TableColumn<Student,String> tableColumnFirstName, tableColumnSecondName, tableColumnNumber;
    @FXML
    public TableColumn<Student, ComboBox<String>> tableColumnCourse;


    @FXML
    private void onButtonAddStudentClick() throws IOException {
        Stage stage = setupStudentStage(Action.Create);
        stage.show();
    }


    @FXML
    private void onButtonEditStudentClick() throws IOException {
        Stage stage = setupStudentStage(Action.Edit);
        stage.show();
    }

    @FXML
    private void onButtonDeleteStudentClick() throws IOException {
        Stage stage = setupStudentStage(Action.Delete);
        stage.show();
    }


    private Stage setupStudentStage(Action action) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("manage-student-window.fxml"));
        Parent root; //= loader.load();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // TODO: Ajouter dans le CourseListView la liste de tous les cours disponible et implementer dans l'objet etudiant seulement eu qui son cocher

        switch(action){
            // switch pour setup la fenetre
            case Edit:{
                root = loader.load();
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
            case Create:{
                stage.setTitle("Creer un étudiant");
                root = loader.load();
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
            case Delete:{
                loader = new FXMLLoader(MainWindow.class.getResource("delete-student-window.fxml"));
                root = loader.load();

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
            default:{
                loader = new FXMLLoader(getClass().getResource("manage-student-window.fxml"));
                root = loader.load();
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