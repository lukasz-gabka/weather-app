package application.controller;

import application.model.WeatherData;
import application.view.ViewManager;
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
        Scene scene = errorLabel.getScene();
        String textFromField = typeCityTextField.getText();
        String id = typeCityTextField.getId();

        WeatherData weatherData = new WeatherData();
        weatherData.getWeatherData(textFromField);

        viewManager.addLayoutToScene(new WeatherPaneController(viewManager), scene, textFromField, id);
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
