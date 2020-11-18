package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DefaultPaneController extends BaseController{

    @FXML
    private Label errorLabel;

    @FXML
    void typeCityAction() {

    }

    public DefaultPaneController(ViewManager viewManager, String fxmlName) {
        super(viewManager, fxmlName);
    }
}
