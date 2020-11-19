package application.view;

import application.controller.BaseController;
import application.controller.DefaultPaneController;
import application.controller.MainWindowController;
import application.controller.WeatherPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ViewManager {

    private Parent initializeLayout(BaseController baseController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);

        try {
            Parent parent = fxmlLoader.load();
            return parent;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HBox initializeDefaultWindowOnStartup() {
        MainWindowController mainWindowController = new MainWindowController(this, "MainWindow.fxml");
        DefaultPaneController defaultLeftPaneController = new DefaultPaneController(this, "DefaultPane.fxml");
        DefaultPaneController defaultRightPaneController = new DefaultPaneController(this, "DefaultPane.fxml");

        HBox hBox = (HBox) initializeLayout(mainWindowController);

        AnchorPane leftPane = (AnchorPane) initializeLayout(defaultLeftPaneController);
        changeId(leftPane, "#typeCityTextField", "typeCityLeftTextField");

        AnchorPane rightPane = (AnchorPane) initializeLayout(defaultRightPaneController);
        changeId(rightPane, "#typeCityTextField", "typeCityRightTextField");

        hBox.getChildren().addAll(leftPane, rightPane);

        return hBox;
    }

    public void changeId(Parent parent, String oldId, String newId) {
        Node node = parent.lookup(oldId);
        node.setId(newId);
    }

    public void initializeWeatherWindow(Scene scene, String text, String id) {
        WeatherPaneController controller = new WeatherPaneController(this, "WeatherPane.fxml");

        AnchorPane anchorPane = (AnchorPane) initializeLayout(controller);
        changeId(anchorPane, "#typeCityTextField", id);
        controller.setTypeCityTextField(text);

        int paneIndex;
        if (id.contains("Left"))
            paneIndex = 0;
        else
            paneIndex = 1;

        HBox hBox = (HBox) scene.getRoot();

        hBox.getChildren().set(paneIndex, anchorPane);
    }

    public void initializeDefaultWindow(Scene scene, String id) {
        DefaultPaneController controller = new DefaultPaneController(this, "DefaultPane.fxml");

        AnchorPane anchorPane = (AnchorPane) initializeLayout(controller);
        changeId(anchorPane, "#typeCityTextField", id);

        int paneIndex;
        if (id.contains("Left"))
            paneIndex = 0;
        else
            paneIndex = 1;

        HBox hBox = (HBox) scene.getRoot();

        hBox.getChildren().set(paneIndex, anchorPane);
    }
}