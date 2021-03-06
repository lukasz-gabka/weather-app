package application.view;

import application.controller.*;
import application.model.Persistence;
import application.model.Sleeper;
import application.model.dto.MainDto;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewManager {

    private Sleeper sleeper = new Sleeper();

    public HBox initializeMainLayout(
            Persistence persistence,
            MainPaneController mainPaneController,
            WeatherPaneController leftWeatherPaneController,
            WeatherPaneController rightWeatherPaneController) {

        HBox hBox = (HBox) mainPaneController.getParent();

        if (persistence.isPersistenceLoaded()) {
            String[] cities = persistence.getCities();

            if (cities[0] != null) {
                leftWeatherPaneController.initializeWeatherLayout(cities[0]);
            }
            if (cities[1] != null) {
                rightWeatherPaneController.initializeWeatherLayout(cities[1]);
            }
        }

        Parent leftParent = leftWeatherPaneController.getParent();
        sleeper.sleep(); // weatherbit.io API free version enables only 1 request per second
        Parent rightParent = rightWeatherPaneController.getParent();

        hBox.getChildren().addAll(leftParent, rightParent);

        return hBox;
    }

    public void initializeCurrentWeatherLayout(MainDto data, Tab tab) {
        WeatherDataPaneController controller = new WeatherDataPaneController(this, data, 0);
        Parent parent = controller.getParent();
        tab.setContent(parent);
    }

    public void initializeDailyForecastLayout(VBox vBox, MainDto weatherData) {
        int tomorrowDataIndex = 1;
        int fifthDayDataIndex = weatherData.getData().size() - 1;

        for (int i = tomorrowDataIndex; i <= fifthDayDataIndex; i++) {
            int vBoxIndex = i - 1;
            initializeSingleWeatherDataPane(
                    vBox,
                    new WeatherDataPaneController(this, weatherData, i),
                    vBoxIndex
            );
        }
    }

    public void initializeSingleWeatherDataPane(VBox vBox, BaseController controller, int index) {
        Parent parent = controller.getParent();
        vBox.getChildren().add(index, parent);
    }

    public int getLayoutIndex(Parent parent) {
        Scene scene = parent.getScene();
        HBox hBox = (HBox) scene.getRoot();

        return hBox.getChildren().indexOf(parent);
    }

    public void setSleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
    }
}