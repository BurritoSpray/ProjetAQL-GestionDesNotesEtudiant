package ez.clap.gestionetudiant_aql.controllers;

import ez.clap.gestionetudiant_aql.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Parent root = loader.load();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        switch(action){
            // switch pour setup la fenetre
            case Edit:{
                stage.setTitle("Modifier l'étudiant");
                ManageStudentWindow manageStudentWindow = loader.getController();
                manageStudentWindow.loadUser("Test");
                manageStudentWindow.buttonConfirm.setText("Modifier");
                break;
            }
            case Create:{
                stage.setTitle("Creer un étudiant");
                break;
            }
            case Delete:{
                stage.setTitle("Attention!");
                loader = new FXMLLoader(MainWindow.class.getResource("delete-student-window.fxml"));
                root = loader.load();
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


}