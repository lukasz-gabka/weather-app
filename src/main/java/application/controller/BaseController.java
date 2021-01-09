package application.controller;

import application.view.ViewManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public abstract class BaseController {

    protected ViewManager viewManager;
    protected String fxmlName;
    protected Parent parent;

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

    public Parent getParent() {
        return parent;
    }
}
