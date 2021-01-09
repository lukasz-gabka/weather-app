package application.controller;

import application.model.dto.MainDto;
import application.model.WeatherData;
import application.view.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultPaneController extends BaseController {

    private static final String CURRENT_WEATHER_URL = "http://api.weatherbit.io/v2.0/current?lang=pl";
    private static final String DAILY_FORECAST_URL = "http://api.weatherbit.io/v2.0/forecast/daily?lang=pl&days=6";

    private static final String LOADING_MESSAGE = "Wczytywanie...";

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    void typeCityAction() {
        initializeWeatherLayout(this.typeCityTextField.getText());
    }

    public DefaultPaneController(ViewManager viewManager) {
        super(viewManager, "DefaultPane.fxml");

        this.typeCityTextField.setFocusTraversable(false);
    }

    public void initializeWeatherLayout(String cityName) {
        this.errorLabel.setTextFill(Color.BLACK);
        this.errorLabel.setText(LOADING_MESSAGE);

        Scene scene = this.parent.getScene();
        WeatherData weatherData = new WeatherData();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            MainDto currentWeatherData = weatherData.getWeatherData(cityName, CURRENT_WEATHER_URL);
            sleep(); // weatherbit.io API free version enables only 1 request per second
            MainDto dailyForecastData = weatherData.getWeatherData(cityName, DAILY_FORECAST_URL);

            Platform.runLater(() -> {
                handleWeatherData(scene, currentWeatherData, dailyForecastData);
            });
        });
        executorService.shutdown();
    }

    private void handleWeatherData(Scene scene, MainDto currentWeatherData, MainDto dailyForecastData) {
        if (currentWeatherData.getErrorMessage() == null) {
            String fullCityName = dailyForecastData.getFullCityName();

            WeatherPaneController weatherPaneController = new WeatherPaneController(viewManager, fullCityName, currentWeatherData, dailyForecastData);
            viewManager.changeLayout(scene, this, weatherPaneController);
            weatherPaneController.saveToPersistence(dailyForecastData.getCityName());
            this.errorLabel.setTextFill(Color.RED);
        } else {
            this.errorLabel.setTextFill(Color.RED);
            this.errorLabel.setText(currentWeatherData.getErrorMessage());
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}