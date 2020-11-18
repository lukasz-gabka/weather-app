package application.view;

import application.controller.BaseController;
import application.controller.DefaultPaneController;
import application.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    public HBox initializeDefaultWindow() {
        MainWindowController mainWindowController = new MainWindowController(this, "MainWindow.fxml");
        DefaultPaneController defaultLeftPaneController = new DefaultPaneController(this, "DefaultPane.fxml");
        DefaultPaneController defaultRightPaneController = new DefaultPaneController(this, "DefaultPane.fxml");

        HBox hBox = (HBox) initializeLayout(mainWindowController);
        AnchorPane defaultLeftPane = (AnchorPane) initializeLayout(defaultLeftPaneController);
        AnchorPane defaultRightPane = (AnchorPane) initializeLayout(defaultRightPaneController);

        hBox.getChildren().addAll(defaultLeftPane, defaultRightPane);

        return hBox;
    }
}