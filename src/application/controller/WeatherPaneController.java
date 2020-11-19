package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class WeatherPaneController extends BaseController implements Initializable {

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Button deleteCityButton;

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

    public WeatherPaneController(ViewManager viewManager) {
        super(viewManager, "WeatherPane.fxml");
    }

    public void setTypeCityTextField(String text) {
        typeCityTextField.setText(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCityTextField.setFocusTraversable(false);
        deleteCityButton.setFocusTraversable(false);
    }
}
