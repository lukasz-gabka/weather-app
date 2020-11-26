package application.view;

import application.controller.BaseController;
import application.controller.DefaultPaneController;
import application.controller.MainWindowController;
import application.model.JSONParsedObjects.MainJSONObject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;

public class ViewManager {

    public HBox initializeMainLayoutOnStartup() {
        MainWindowController mainWindowController = new MainWindowController(this);
        HBox hBox = (HBox) mainWindowController.getParent();

        DefaultPaneController defaultLeftPaneController = new DefaultPaneController(this);
        Parent leftParent = defaultLeftPaneController.getParent();

        DefaultPaneController defaultRightPaneController = new DefaultPaneController(this);
        Parent rightParent = defaultRightPaneController.getParent();

        hBox.getChildren().addAll(leftParent, rightParent);

        return hBox;
    }

    public void changeLayout(Scene scene, BaseController currentController, BaseController newController) {
        Parent newParent = newController.getParent();
        Parent currentParent = currentController.getParent();

        HBox hBox = (HBox) scene.getRoot();
        int index = hBox.getChildren().indexOf(currentParent);

        hBox.getChildren().remove(currentParent);
        hBox.getChildren().add(index, newParent);
    }

    public void initializeWeatherLayout(MainJSONObject object, Tab tab, BaseController currentWeatherController) {
        Parent currentWeatherParent = currentWeatherController.getParent();
        tab.setContent(currentWeatherParent);
    }
}