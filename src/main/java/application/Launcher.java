package application;

import application.model.Persistence;
import application.view.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Persistence persistence = new Persistence();

    @Override
    public void start(Stage stage) {
        if (Persistence.isFileExists()) {
            persistence.loadFromPersistence();
        }

        ViewManager viewManager = new ViewManager();
        Scene scene = new Scene(viewManager.initializeMainLayout());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Doradca pogodowy");
        stage.show();

        persistence.initializeWithPersistence();
    }

    public void stop() {
        persistence.saveToPersistence();
    }
}