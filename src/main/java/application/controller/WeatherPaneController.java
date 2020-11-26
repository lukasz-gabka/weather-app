package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class WeatherPaneController extends BaseController implements Initializable {

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button deleteCityButton;

    @FXML
    private TabPane weatherTabPane;

    @FXML
    private Tab currentWeatherTab;

    @FXML
    private VBox dailyForecastVBox;

    @FXML
    void deleteCityAction() {
        Scene scene = typeCityTextField.getScene();

        viewManager.changeLayout(scene, this, new DefaultPaneController(viewManager));
    }

    public WeatherPaneController(ViewManager viewManager, String text) {
        super(viewManager, "WeatherPane.fxml");
        typeCityTextField.setText(text);
    }

    public void setTypeCityTextField(String text) {
        typeCityTextField.setText(text);
    }

    public TabPane getWeatherTabPane() {
        return weatherTabPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCityTextField.setFocusTraversable(false);
        deleteCityButton.setFocusTraversable(false);
    }
}
