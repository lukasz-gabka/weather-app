package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DefaultPaneController extends BaseController{

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    void typeCityAction() {

    }

    public DefaultPaneController(ViewManager viewManager, String fxmlName) {
        super(viewManager, fxmlName);
    }
}
