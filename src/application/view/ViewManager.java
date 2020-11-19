package application.view;

import application.controller.BaseController;
import application.controller.DefaultPaneController;
import application.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ViewManager {

    private final String TEXT_FIELD_DEFAULT_ID = "#typeCityTextField";

    private Parent initializeLayout(BaseController baseController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);

        try {
            return fxmlLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HBox initializeMainLayout() {
        MainWindowController mainWindowController = new MainWindowController(this);

        return (HBox) initializeLayout(mainWindowController);
    }

    public HBox initializeMainLayoutOnStartup() {
        DefaultPaneController defaultLeftPaneController = new DefaultPaneController(this);
        DefaultPaneController defaultRightPaneController = new DefaultPaneController(this);

        HBox hBox = initializeMainLayout();

        AnchorPane leftPane = (AnchorPane) initializeLayout(defaultLeftPaneController);
        changeId(leftPane, TEXT_FIELD_DEFAULT_ID, "typeCityLeftTextField");

        AnchorPane rightPane = (AnchorPane) initializeLayout(defaultRightPaneController);
        changeId(rightPane, TEXT_FIELD_DEFAULT_ID, "typeCityRightTextField");

        hBox.getChildren().addAll(leftPane, rightPane);

        return hBox;
    }

    private void changeId(Parent parent, String oldId, String newId) {
        Node node = parent.lookup(oldId);
        node.setId(newId);
    }

    public void addLayoutToScene(BaseController controller, Scene scene, String text, String id) {
        Parent parent = initializeLayout(controller);

        changeId(parent, TEXT_FIELD_DEFAULT_ID, id);
        controller.setTypeCityTextField(text);

        int paneIndex;
        if (id.contains("Left"))
            paneIndex = 0;
        else
            paneIndex = 1;

        HBox hBox = (HBox) scene.getRoot();
        hBox.getChildren().set(paneIndex, parent);
    }
}