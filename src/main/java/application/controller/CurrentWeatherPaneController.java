package application.controller;

import application.model.dto.DataDto;
import application.model.dto.MainDto;
import application.model.dto.WeatherDto;
import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CurrentWeatherPaneController extends BaseController {

    private static final String DEGREE_CELCIUS = "\u00B0C";
    private static final String PATH_TO_ICONS = "/images/icons/";

    @FXML
    private ImageView weatherImageView;

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
    private ImageView arrowImageView;

    @FXML
    private Label windSpeedLabel;

    public CurrentWeatherPaneController(ViewManager viewManager, MainDto data) {
        super(viewManager, "CurrentWeatherPane.fxml");

        setDataToWeatherLayout(data);
    }

    private void setDataToWeatherLayout(MainDto data) {
        DataDto dataObject = data.getData().get(0);
        WeatherDto weatherObject = dataObject.getWeather();

        String iconPath = PATH_TO_ICONS + weatherObject.getIcon() + ".png";
        String temperature = dataObject.getTemp() + DEGREE_CELCIUS;
        String description = weatherObject.getDescription();
        String pressure = dataObject.getPres() + " hPa";
        String clouds = dataObject.getClouds() + "%";
        String appTemperature = dataObject.getAppTemp() + DEGREE_CELCIUS;
        String humidity = dataObject.getRh() + "%";
        String windSpeed = dataObject.getWindSpd() + " km/h";
        double windDirection = dataObject.getWindDir();
        Image image = new Image(iconPath);

        weatherImageView.setImage(image);
        temperatureLabel.setText(temperature);
        descriptionLabel.setText(description);
        pressureLabel.setText(pressure);
        cloudsLabel.setText(clouds);
        appTemperatureLabel.setText(appTemperature);
        humidityLabel.setText(humidity);
        windSpeedLabel.setText(windSpeed);
        arrowImageView.setRotate(windDirection);
    }
}