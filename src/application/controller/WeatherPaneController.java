package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class WeatherPaneController extends BaseController{

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private VBox weatherVBox;

    @FXML
    void deleteCityAction() {
        Scene scene = errorLabel.getScene();
        String id = typeCityTextField.getId();

        viewManager.initializeDefaultWindow(scene, id);
    }

    public WeatherPaneController(ViewManager viewManager, String fxmlName) {
        super(viewManager, fxmlName);
    }

    public void setTypeCityTextField(String text) {
        typeCityTextField.setText(text);
    }
}
