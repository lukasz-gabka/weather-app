package application.controller;

import application.model.JSONParsedObjects.DataJSON;
import application.model.JSONParsedObjects.MainJSON;
import application.model.JSONParsedObjects.WeatherJSON;
import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DailyForecastPaneController extends BaseController {

    private final String DEGREE_CELCIUS = "\u00B0C";
    private final String PATH_TO_ICONS = "/images/icons/";

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
    private Label humidityLabel;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private ImageView arrowImageView;

    @FXML
    private Label dateLabel;

    public DailyForecastPaneController(ViewManager viewManager, MainJSON data, int index) {
        super(viewManager, "DailyForecastPane.fxml");

        setDataToWeatherLayout(data, index);
    }

    public void setDataToWeatherLayout(MainJSON data, int index) {
        DataJSON dataObject = data.getData().get(index);
        WeatherJSON weatherObject = dataObject.getWeather();

        String iconPath = PATH_TO_ICONS + weatherObject.getIcon() + ".png";
        String temperature = dataObject.getTemp() + DEGREE_CELCIUS;
        String description = weatherObject.getDescription();
        String pressure = dataObject.getPres() + " hPa";
        String clouds = dataObject.getClouds() + "%";
        String humidity = dataObject.getRh() + "%";
        String windSpeed = dataObject.getWind_spd() + " km/h";
        double windDirection = dataObject.getWind_dir();
        Image image = new Image(iconPath);
        String datetime = dataObject.getDatetime();

        weatherImageView.setImage(image);
        temperatureLabel.setText(temperature);
        descriptionLabel.setText(description);
        pressureLabel.setText(pressure);
        cloudsLabel.setText(clouds);
        humidityLabel.setText(humidity);
        windSpeedLabel.setText(windSpeed);
        arrowImageView.setRotate(windDirection);
        dateLabel.setText(datetime);
    }
}
