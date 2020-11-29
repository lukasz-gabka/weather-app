package application.controller;

import application.model.JSONParsedObjects.MainJSONObject;
import application.model.WeatherData;
import application.view.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DefaultPaneController extends BaseController implements Initializable {

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    void typeCityAction() throws InterruptedException{
        errorLabel.setText("");

        Scene scene = errorLabel.getScene();
        String textFromField = typeCityTextField.getText();
        WeatherData weatherData = new WeatherData();

        Thread thread = new Thread(() -> {
            MainJSONObject currentWeatherData = weatherData.getWeatherData(textFromField, weatherData.CURRENT_WEATHER_URL);
            try {
                Thread.sleep(1000); // weatherbit.io API free version enables only 1 request per second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MainJSONObject dailyForecastData = weatherData.getWeatherData(textFromField, weatherData.DAILY_FORECAST_URL);

            Platform.runLater(() -> {
                if (currentWeatherData.getErrorMessage() == null) {
                    String fullCityName = dailyForecastData.getFullCityName();

                    viewManager.changeLayout(scene, this, new WeatherPaneController(viewManager, fullCityName, currentWeatherData, dailyForecastData));
                } else {
                    errorLabel.setText(currentWeatherData.getErrorMessage());
                }
            });
        });
        thread.start();
    }

    public DefaultPaneController(ViewManager viewManager) {
        super(viewManager, "DefaultPane.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCityTextField.setFocusTraversable(false);
    }

    public void setErrorLabel(String text) {
        errorLabel.setText(text);
    }
}