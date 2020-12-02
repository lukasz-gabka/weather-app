package application.controller;

import application.model.JSONParsedObjects.MainJSON;
import application.model.Persistence;
import application.model.WeatherData;
import application.view.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class DefaultPaneController extends BaseController {

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    void typeCityAction() {
        initializeWeatherLayout(this.typeCityTextField.getText());
    }

    public DefaultPaneController(ViewManager viewManager, String cityName) {
        super(viewManager, "DefaultPane.fxml");

        this.typeCityTextField.setFocusTraversable(false);

        Persistence.addController(this);

        if (cityName != null) {
            initializeWeatherLayout(cityName);
        }
    }

    public void initializeWeatherLayout(String input) {
        this.errorLabel.setTextFill(Color.BLACK);
        this.errorLabel.setText("Wczytywanie...");

        Scene scene = this.parent.getScene();
        WeatherData weatherData = new WeatherData();

        Thread thread = new Thread(() -> {
            MainJSON currentWeatherData = weatherData.getWeatherData(input, weatherData.CURRENT_WEATHER_URL);
            try {
                Thread.sleep(1000); // weatherbit.io API free version enables only 1 request per second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MainJSON dailyForecastData = weatherData.getWeatherData(input, weatherData.DAILY_FORECAST_URL);

            Platform.runLater(() -> {
                if (currentWeatherData.getErrorMessage() == null) {
                    String fullCityName = dailyForecastData.getFullCityName();

                    WeatherPaneController weatherPaneController = new WeatherPaneController(viewManager, fullCityName, currentWeatherData, dailyForecastData);
                    viewManager.changeLayout(scene, this, weatherPaneController);
                    weatherPaneController.saveToPersistence(dailyForecastData.getCity_name());
                    this.errorLabel.setTextFill(Color.RED);
                } else {
                    this.errorLabel.setTextFill(Color.RED);
                    this.errorLabel.setText(currentWeatherData.getErrorMessage());
                }
            });
        });
        thread.start();
    }
}