package ez.clap.gestionetudiant_aql;

import ez.clap.gestionetudiant_aql.controllers.MainWindowController;
import ez.clap.gestionetudiant_aql.entities.Course;
import ez.clap.gestionetudiant_aql.entities.Student;
import ez.clap.gestionetudiant_aql.utilities.Data;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
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
        MainWindowController mainWindowController = fxmlLoader.getController();
        mainWindowController.loadWindow();
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

}