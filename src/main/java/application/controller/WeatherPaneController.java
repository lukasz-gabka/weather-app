package application.controller;

import application.model.dto.MainDto;
import application.model.Persistence;
import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class WeatherPaneController extends BaseController {

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button deleteCityButton;

    @FXML
    private TabPane weatherTabPane;

    @FXML
    private Tab currentWeatherTab;

    @FXML
    private ScrollPane weatherScrollPane;

    @FXML
    private VBox dailyForecastVBox;

    @FXML
    void deleteCityAction() {
        Scene scene = this.parent.getScene();

        deleteFromPersistence(scene);
        viewManager.changeLayout(scene, this, new DefaultPaneController(viewManager));
    }

    public WeatherPaneController(ViewManager viewManager, String cityName, MainDto currentWeatherData, MainDto dailyForecastData) {
        super(viewManager, "WeatherPane.fxml");

        this.typeCityTextField.setFocusTraversable(false);
        this.deleteCityButton.setFocusTraversable(false);
        this.typeCityTextField.setText(cityName);

        viewManager.initializeCurrentWeatherLayout(this.currentWeatherTab, new CurrentWeatherPaneController(viewManager, currentWeatherData));
        addDailyForecastDataToLayout(viewManager, dailyForecastData);
    }

    private void addDailyForecastDataToLayout(ViewManager viewManager, MainDto dailyForecastData) {
        int tomorrowDataIndex = 1;
        int fifthDayDataIndex = dailyForecastData.getData().size() - 1;

        for (int i = tomorrowDataIndex; i <= fifthDayDataIndex; i++) {
            int weatherPaneVBoxIndex = i - 1;
            viewManager.initializeDailyForecastLayout(
                    this.dailyForecastVBox,
                    new DailyForecastPaneController(viewManager, dailyForecastData, i),
                    weatherPaneVBoxIndex
            );
        }
    }

    public void saveToPersistence(String cityName) {
        Persistence persistence = new Persistence();

        int index = viewManager.getLayoutIndex(this.parent);
        persistence.setCityName(cityName, index);
    }

    public void deleteFromPersistence(Scene scene) {
        Persistence persistence = new Persistence();

        int index = viewManager.getLayoutIndex(this.parent);
        persistence.setCityName(null, index);
    }
}
