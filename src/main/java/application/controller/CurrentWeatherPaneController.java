package application.controller;

import application.model.JSONParsedObjects.DataJSONObject;
import application.model.JSONParsedObjects.MainJSONObject;
import application.model.JSONParsedObjects.WeatherJSONObject;
import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CurrentWeatherPaneController extends BaseController {

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

    public CurrentWeatherPaneController(ViewManager viewManager, MainJSONObject weatherData) {
        super(viewManager, "CurrentWeatherPane.fxml");

        setDataToWeatherLayout(weatherData);
    }

    public void setDataToWeatherLayout(MainJSONObject object) {
        DataJSONObject dataObject = object.getData().get(0);
        WeatherJSONObject weatherObject = dataObject.getWeather();

        //weatherImageView.setImage();
        String temperature = Float.toString(dataObject.getTemp());
        String description = weatherObject.getDescription();
        String pressure = Float.toString(dataObject.getPres());
        String clouds = Integer.toString(dataObject.getClouds());

        temperatureLabel.setText(temperature);
        descriptionLabel.setText(description);
        pressureLabel.setText(pressure);
        cloudsLabel.setText(clouds);
    }
}