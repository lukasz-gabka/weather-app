package application.controller;

import application.model.JSONParsedObjects.MainJSON;
import application.model.WeatherData;
import application.view.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DefaultPaneController extends BaseController {

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    void typeCityAction() {
        this.errorLabel.setText("");

        Scene scene = this.parent.getScene();
        String userInput = this.typeCityTextField.getText();
        WeatherData weatherData = new WeatherData();

        Thread thread = new Thread(() -> {
            MainJSON currentWeatherData = weatherData.getWeatherData(userInput, weatherData.CURRENT_WEATHER_URL);
            try {
                Thread.sleep(1000); // weatherbit.io API free version enables only 1 request per second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MainJSON dailyForecastData = weatherData.getWeatherData(userInput, weatherData.DAILY_FORECAST_URL);

            Platform.runLater(() -> {
                if (currentWeatherData.getErrorMessage() == null) {
                    String fullCityName = dailyForecastData.getFullCityName();

                    WeatherPaneController weatherPaneController = new WeatherPaneController(viewManager, fullCityName, currentWeatherData, dailyForecastData);
                    viewManager.changeLayout(scene, this, weatherPaneController);
                    weatherPaneController.saveToPersistence(userInput);
                } else {
                    this.errorLabel.setText(currentWeatherData.getErrorMessage());
                }
            });
        });
                thread.start();
    }

    public DefaultPaneController(ViewManager viewManager) {
        super(viewManager, "DefaultPane.fxml");

        this.typeCityTextField.setFocusTraversable(false);
    }
}