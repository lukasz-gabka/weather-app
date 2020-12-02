package application.controller;

import application.model.JSONParsedObjects.MainJSON;
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
        viewManager.changeLayout(scene, this, new DefaultPaneController(viewManager, null));
    }

    public WeatherPaneController(ViewManager viewManager, String cityName, MainJSON currentWeatherData, MainJSON dailyForecastData) {
        super(viewManager, "WeatherPane.fxml");

        this.typeCityTextField.setFocusTraversable(false);
        this.deleteCityButton.setFocusTraversable(false);

        this.typeCityTextField.setText(cityName);

        viewManager.initializeCurrentWeatherLayout(this.currentWeatherTab, new CurrentWeatherPaneController(viewManager, currentWeatherData));

        int arrayLength = dailyForecastData.getData().size();
        for (int i = 1; i <= arrayLength - 1; i++) {
            viewManager.initializeDailyForecastLayout(this.dailyForecastVBox, new DailyForecastPaneController(viewManager, dailyForecastData, i), i - 1);
        }
    }

    public void saveToPersistence(String cityName) {
        int index = viewManager.getLayoutIndex(this.parent);
        Persistence.setCityName(cityName, index);
    }

    public void deleteFromPersistence(Scene scene) {
        int index = viewManager.getLayoutIndex(this.parent);
        Persistence.setCityName(null, index);
    }
}
