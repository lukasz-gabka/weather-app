package application.view;

import application.controller.MainPaneController;
import application.controller.WeatherDataPaneController;
import application.controller.WeatherPaneController;
import application.model.Persistence;
import application.model.dto.MainDto;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mainDtoTestGenerator.MainDtoTestGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewManagerTest {

    //instantiation of jxfPanel is necessary to use controllers with JavaFX controls in test scope
    //without jxfPanel, ExceptionInInitializerError and IllegalStateException is thrown
    JFXPanel jfxPanel = new JFXPanel();

    String[] cities = {"Berlin", "Paris"};
    String[] citiesWithIndex0Null = {null, "Paris"};
    String[] citiesWithIndex1Null = {"Berlin", null};

    @InjectMocks
    ViewManager viewManager = new ViewManager();

    @Mock
    MainPaneController mainPaneController;

    @Mock
    WeatherPaneController leftWeatherPaneController;

    @Mock
    WeatherPaneController rightWeatherPaneController;

    @Mock
    WeatherDataPaneController weatherDataPaneController;

    @Mock
    Persistence persistence;

    @Test
    void shouldReturnHBoxWithTwoParentsAsChildren() {
        //given
        //when
        prepareMocks();
        given(persistence.isPersistenceLoaded()).willReturn(false);
        HBox hBox = viewManager.initializeMainLayout(
                persistence,
                mainPaneController,
                leftWeatherPaneController,
                rightWeatherPaneController
        );

        //then
        assertThat(hBox.getChildren(), hasSize(2));
    }

    @Test
    void shouldCallInitializeWeatherLayoutMethodTwoTimesIfCitiesArrayIsNotNull() {
        //given
        prepareMocks();
        given(persistence.isPersistenceLoaded()).willReturn(true);
        given(persistence.getCities()).willReturn(cities);

        //when
        HBox hBox = viewManager.initializeMainLayout(
                persistence,
                mainPaneController,
                leftWeatherPaneController,
                rightWeatherPaneController
        );

        //then
        verify(leftWeatherPaneController).initializeWeatherLayout(cities[0]);
        verify(rightWeatherPaneController).initializeWeatherLayout(cities[1]);
    }

    @Test
    void shouldCallInitializeWeatherLayoutMethodOnceIfCitiesArrayIndex0IsNull() {
        //given
        prepareMocks();
        given(persistence.isPersistenceLoaded()).willReturn(true);
        given(persistence.getCities()).willReturn(citiesWithIndex0Null);

        //when
        HBox hBox = viewManager.initializeMainLayout(
                persistence,
                mainPaneController,
                leftWeatherPaneController,
                rightWeatherPaneController
        );

        //then
        verify(leftWeatherPaneController, never()).initializeWeatherLayout(cities[0]);
        verify(rightWeatherPaneController).initializeWeatherLayout(cities[1]);
    }

    @Test
    void shouldCallInitializeWeatherLayoutMethodOnceIfCitiesArrayIndex1IsNull() {
        //given
        prepareMocks();
        given(persistence.isPersistenceLoaded()).willReturn(true);
        given(persistence.getCities()).willReturn(citiesWithIndex1Null);

        //when
        HBox hBox = viewManager.initializeMainLayout(
                persistence,
                mainPaneController,
                leftWeatherPaneController,
                rightWeatherPaneController
        );

        //then
        verify(leftWeatherPaneController).initializeWeatherLayout(cities[0]);
        verify(rightWeatherPaneController, never()).initializeWeatherLayout(cities[1]);
    }

    @Test
    void shouldSetParentToTabWhenInitializingCurrentWeatherLayout() {
        //given
        MainDtoTestGenerator generator = new MainDtoTestGenerator();
        MainDto currentWeatherData = generator.getCurrentWeatherData();

        Tab tab = new Tab();

        //when
        viewManager.initializeCurrentWeatherLayout(currentWeatherData, tab);

        //then
        assertThat(tab.getContent(), notNullValue());
    }

    @Test
    void shouldAddWeatherDataPaneToVBox() {
        //given
        given(weatherDataPaneController.getParent()).willReturn(new Pane());
        VBox vBox = new VBox();
        int index = 0;

        //when
        viewManager.initializeSingleWeatherDataPane(vBox, weatherDataPaneController, index);

        //then
        assertThat(vBox.getChildren().size(), equalTo(1));
    }

    @Test
    void shouldAddToVBox5WeatherDataPanes() {
        //given
        MainDtoTestGenerator generator = new MainDtoTestGenerator();
        MainDto dailyForecastData = generator.getDailyForecastData();
        VBox vBox = new VBox();

        //when
        viewManager.initializeDailyForecastLayout(vBox, dailyForecastData);

        //then
        assertThat(vBox.getChildren().size(), equalTo(5));
    }

    @Test
    void shouldReturnIndexOfParentInsideHBox() {
        //given
        Pane pane0 = new Pane();
        Pane pane1 = new Pane();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(pane0, pane1);

        Scene scene = new Scene(hBox);

        //when
        int index0 = viewManager.getLayoutIndex(pane0);
        int index1 = viewManager.getLayoutIndex(pane1);

        //then
        assertThat(index0, equalTo(0));
        assertThat(index1, equalTo(1));

    }

    private void prepareMocks() {
        given(mainPaneController.getParent()).willReturn(new HBox());
        given(leftWeatherPaneController.getParent()).willReturn(new Pane());
        given(rightWeatherPaneController.getParent()).willReturn(new Pane());
    }
}