package application;

import application.controller.MainPaneController;
import application.controller.WeatherPaneController;
import application.model.Persistence;
import application.model.WeatherData;
import application.view.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Persistence persistence = new Persistence();

    @Override
    public void start(Stage stage) {
        if (persistence.isFileExist()) {
            persistence.loadFromPersistence();
        }

        ViewManager viewManager = new ViewManager();
        HBox mainLayout = viewManager.initializeMainLayout(
                persistence,
                new MainPaneController(viewManager),
                new WeatherPaneController(viewManager, persistence, new WeatherData()),
                new WeatherPaneController(viewManager, persistence, new WeatherData())
        );
        Scene scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Doradca Pogodowy");
        stage.show();
    }

    public void stop() {
        persistence.saveToPersistence();
    }
}