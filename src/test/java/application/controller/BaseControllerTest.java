package application.controller;

import application.view.ViewManager;
import org.junit.jupiter.api.Test;

import javax.swing.text.View;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class BaseControllerTest {

    private static class TestPaneController extends BaseController {

        public TestPaneController(ViewManager viewManager, String fxmlName) {
            super(viewManager, fxmlName);
        }
    }

    ViewManager viewManager = new ViewManager();

    @Test
    void shouldCreateLayoutWhenCreatingNewController() {
        //given
        //when
        BaseController controller = new TestPaneController(viewManager, "TestPane.fxml");

        //then
        assertThat(controller.getParent(), notNullValue());
    }
}