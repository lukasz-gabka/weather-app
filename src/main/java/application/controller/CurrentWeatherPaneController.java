package application.controller;

import application.model.JSONParsedObjects.DataJSONObject;
import application.model.JSONParsedObjects.MainJSONObject;
import application.model.JSONParsedObjects.WeatherJSONObject;
import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CurrentWeatherPaneController extends BaseController {

    private final String DEGREE_CELCIUS = "\u00B0C";

    @FXML
    private ImageView WeatherImageView;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label pressureLabel;

    @FXML
    private Label cloudsLabel;

    @FXML
    private Label appTemperatureLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private Label windDirectionLabel;

    public CurrentWeatherPaneController(ViewManager viewManager, MainJSONObject weatherData) {
        super(viewManager, "CurrentWeatherPane.fxml");

        setDataToWeatherLayout(weatherData);
    }

    public void setDataToWeatherLayout(MainJSONObject object) {
        DataJSONObject dataObject = object.getData().get(0);
        WeatherJSONObject weatherObject = dataObject.getWeather();

        //weatherImageView.setImage();
        String temperature = Integer.toString(dataObject.getTemp()) + DEGREE_CELCIUS;
        String description = weatherObject.getDescription();
        String pressure = Integer.toString(dataObject.getPres()) + " hPa";
        String clouds = Integer.toString(dataObject.getClouds()) + "%";
        String appTemperature = Integer.toString(dataObject.getApp_temp()) + DEGREE_CELCIUS;
        String humidity = Integer.toString(dataObject.getRh()) + "%";
        String windSpeed = Integer.toString(dataObject.getWind_spd()) + " m/s";
        String windDirection = Integer.toString(dataObject.getWind_dir());

        temperatureLabel.setText(temperature);
        descriptionLabel.setText(description);
        pressureLabel.setText(pressure);
        cloudsLabel.setText(clouds);
        appTemperatureLabel.setText(appTemperature);
        humidityLabel.setText(humidity);
        windSpeedLabel.setText(windSpeed);
        windDirectionLabel.setText(windDirection);
    }
}