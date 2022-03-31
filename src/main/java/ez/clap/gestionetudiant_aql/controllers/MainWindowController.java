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
import java.util.ArrayList;

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
    public TableColumn<Student, ArrayList<String>> tableColumnCourse;


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

        switch(action){
            // switch pour setup la fenetre
            case Edit:{
                root = loader.load();
                stage.setTitle("Modifier l'étudiant");
                ManageStudentController manageStudentController = loader.getController();
                manageStudentController.loadStudent(tableViewStudent.getSelectionModel().getSelectedItem());
                manageStudentController.buttonConfirm.setText("Modifier");
                break;
            }
            case Create:{
                stage.setTitle("Creer un étudiant");
                root = loader.load();
                ManageStudentController manageStudentController = loader.getController();

                manageStudentController.buttonConfirm.setOnAction(event -> {
                    if(isStudentFieldsValid(manageStudentController)){
                        addStudent(new Student(manageStudentController.textFieldFirstName.getText(),
                                manageStudentController.textFieldSecondName.getText(),
                                manageStudentController.textFieldNumber.getText()));

                        ((Stage)manageStudentController.buttonConfirm.getScene().getWindow()).close();
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
                deleteStudentController.buttonConfirm.setOnAction(event->{
                    Student selectedStudent = tableViewStudent.getSelectionModel().getSelectedItem();
                    Data.getStudentList().remove(selectedStudent);
                    tableViewStudent.getItems().remove(selectedStudent);
                    ((Stage)deleteStudentController.buttonConfirm.getScene().getWindow()).close();
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

    private void addStudent(Student student){
        Data.getStudentList().add(student);
        tableViewStudent.getItems().setAll(Data.getStudentList());
    }


}