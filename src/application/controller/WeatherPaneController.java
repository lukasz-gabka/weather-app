package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WeatherPaneController extends BaseController{

    @FXML
    private Label errorLabel;

    @FXML
    void typeCityAction() {

    }

    public WeatherPaneController(ViewManager viewManager, String fxmlName) {
        super(viewManager, fxmlName);
    }
}
