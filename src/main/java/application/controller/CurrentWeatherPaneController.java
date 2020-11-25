package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CurrentWeatherPaneController extends BaseController {

    @FXML
    private ImageView WeatherImage;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label pressureLabel;

    @FXML
    private Label cloudsLabel;

    public CurrentWeatherPaneController(ViewManager viewManager) {
        super(viewManager, "CurrentWeatherPane.fxml");
    }
}
