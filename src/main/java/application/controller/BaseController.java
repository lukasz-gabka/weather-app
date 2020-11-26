package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;

import java.io.IOException;

public abstract class BaseController {

    protected ViewManager viewManager;
    private String fxmlName;
    private Parent parent;

    public BaseController(ViewManager viewManager, String fxmlName) {
        this.viewManager = viewManager;
        this.fxmlName = fxmlName;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlName));
        fxmlLoader.setController(this);

        try {
            parent = fxmlLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setTypeCityTextField(String text) {}

    public TabPane getWeatherTabPane() {
        return null;
    }

    public String getFxmlName() {
        return fxmlName;
    }

    public Parent getParent() {
        return parent;
    }
}
