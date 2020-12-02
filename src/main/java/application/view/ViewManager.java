package application.view;

import application.controller.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewManager {

    public HBox initializeMainLayout() {
        MainPaneController mainPaneController = new MainPaneController(this);
        HBox hBox = (HBox) mainPaneController.getParent();

        DefaultPaneController defaultLeftPaneController = new DefaultPaneController(this, null);
        Parent leftParent = defaultLeftPaneController.getParent();

        DefaultPaneController defaultRightPaneController = new DefaultPaneController(this, null);
        Parent rightParent = defaultRightPaneController.getParent();

        hBox.getChildren().addAll(leftParent, rightParent);

        return hBox;
    }

    public void changeLayout(Scene scene, BaseController currentController, BaseController newController) {
        Parent newParent = newController.getParent();
        Parent currentParent = currentController.getParent();

        int index = getLayoutIndex(currentParent);
        HBox hBox = (HBox) scene.getRoot();

        hBox.getChildren().remove(currentParent);
        hBox.getChildren().add(index, newParent);
    }

    public void initializeCurrentWeatherLayout(Tab tab, BaseController controller) {
        Parent parent = controller.getParent();
        tab.setContent(parent);
    }

    public void initializeDailyForecastLayout(VBox vBox, BaseController controller, int index) {
        Parent parent = controller.getParent();
        vBox.getChildren().add(index, parent);
    }

    public int getLayoutIndex(Parent parent) {
        Scene scene = parent.getScene();
        HBox hBox = (HBox) scene.getRoot();

        return hBox.getChildren().indexOf(parent);
    }
}