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
    void typeCityAction() {
        errorLabel.setText("");

        Scene scene = errorLabel.getScene();
        String textFromField = typeCityTextField.getText();
        WeatherData weatherData = new WeatherData();

        Thread thread = new Thread(() -> {
            MainJSONObject object = weatherData.getWeatherData(textFromField, weatherData.CURRENT_WEATHER_URL);

            Platform.runLater(() -> {
                if (object.getErrorMessage() == null) {
                    String fullCityName = object.getFullCityName();

                    viewManager.changeLayout(scene, this, new WeatherPaneController(viewManager, fullCityName, object));
                } else {
                    errorLabel.setText(object.getErrorMessage());
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