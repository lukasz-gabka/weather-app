package application.controller;

import application.model.dto.DataDto;
import application.model.dto.MainDto;
import application.model.dto.WeatherDto;
import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WeatherDataPaneController extends BaseController {

    private static final String DEGREE_CELSIUS = "\u00B0C";
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
    private Label humidityLabel;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private ImageView arrowImageView;

    @FXML
    private Label dateLabel;

    public WeatherDataPaneController(ViewManager viewManager, MainDto mainDto, int index) {
        super(viewManager, "WeatherDataPane.fxml");

        DataDto dataObject = mainDto.getData().get(index);

        setDataToWeatherLayout(dataObject);
    }

    private void setDataToWeatherLayout(DataDto dataDto) {
        WeatherDto weatherObject = dataDto.getWeather();

        String iconPath = PATH_TO_ICONS + weatherObject.getIcon() + ".png";
        String temperature = dataDto.getTemp() + DEGREE_CELSIUS;
        String description = weatherObject.getDescription();
        String pressure = dataDto.getPres() + " hPa";
        String clouds = dataDto.getClouds() + "%";
        String humidity = dataDto.getRh() + "%";
        String windSpeed = dataDto.getWindSpd() + " km/h";
        double windDirection = dataDto.getWindDir();
        Image image = new Image(iconPath);
        String date = dataDto.getDate();

        weatherImageView.setImage(image);
        temperatureLabel.setText(temperature);
        descriptionLabel.setText(description);
        pressureLabel.setText(pressure);
        cloudsLabel.setText(clouds);
        humidityLabel.setText(humidity);
        windSpeedLabel.setText(windSpeed);
        arrowImageView.setRotate(windDirection);
        dateLabel.setText(date);
    }
}
