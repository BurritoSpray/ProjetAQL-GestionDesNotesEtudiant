package ez.clap.gestionetudiant_aql;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Gestionnaire d'étudiants");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        System.out.println("Hello World!");
    }

    public static void main(String[] args) {
        launch();
    }
}