package application.view;

import application.controller.*;
import application.model.Persistence;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewManager {

    public HBox initializeMainLayout(boolean isPersistenceLoaded, Persistence persistence) {
        MainPaneController mainPaneController = new MainPaneController(this);
        HBox hBox = (HBox) mainPaneController.getParent();

        WeatherPaneController leftWeatherPaneController = new WeatherPaneController(this, persistence);
        WeatherPaneController rightWeatherPaneController = new WeatherPaneController(this, persistence);

        if (isPersistenceLoaded) {
            String[] cities = persistence.getCities();

            if (cities[0] != null) {
                leftWeatherPaneController.initializeWeatherLayout(cities[0]);
            }
            if (cities[1] != null) {
                rightWeatherPaneController.initializeWeatherLayout(cities[1]);
            }
        }

        Parent leftParent = leftWeatherPaneController.getParent();
        Parent rightParent = rightWeatherPaneController.getParent();

        hBox.getChildren().addAll(leftParent, rightParent);

        return hBox;
    }

    public void initializeDailyForecastLayout(VBox vBox, BaseController controller, int index) {
        Parent parent = controller.getParent();
        vBox.getChildren().add(index, parent);
    }

    public int getLayoutIndex(Parent parent) {
        Scene scene = parent.getScene();
        HBox hBox = (HBox) scene.getRoot();

        return hBox.getChildren().indexOf(parent);
    }
}