package application.controller;

import application.model.Persistence;
import application.model.WeatherData;
import application.model.dto.MainDto;
import application.view.ViewManager;
import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mainDtoTestGenerator.MainDtoTestGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherPaneControllerTest {

    //instantiation of jxfPanel is necessary to use controllers with JavaFX controls in test scope
    //without jxfPanel, ExceptionInInitializerError and IllegalStateException is thrown
    JFXPanel jfxPanel = new JFXPanel();

    private final String CITY = "Katowice";
    private final MainDtoTestGenerator generator = new MainDtoTestGenerator();

    @Mock
    Persistence persistence;

    @Mock
    ViewManager viewManager;

    @Mock
    WeatherData weatherData;

    @InjectMocks
    @Spy
    WeatherPaneController weatherPaneController;

    @AfterEach
    void resetMocks() {
        reset(weatherPaneController, weatherData);
    }

    @Test
    void shouldSetControlsOnControllerCreation() {
        //given
        //when
        //then
        assertThat(weatherPaneController.getDeleteCityButton().isVisible(), equalTo(false));
        assertThat(weatherPaneController.getWeatherTabPane().isVisible(), equalTo(false));
    }

    @Test
    void shouldInitializeWeatherLayoutWhenTypingCityName() {
        //given
        doNothing().when(weatherPaneController).initializeWeatherLayout(anyString());
        weatherPaneController.getTypeCityTextField().setText(CITY);

        //when
        weatherPaneController.typeCityAction();

        //then
        verify(weatherPaneController).initializeWeatherLayout(CITY);
    }

    @Test
    void shouldNotInitializeWeatherLayoutWhenTypingNullCityName() {
        //given
        //when
        weatherPaneController.typeCityAction();

        //then
        verify(weatherPaneController, never()).initializeWeatherLayout(anyString());
    }

    @Test
    void shouldSaveToPersistenceWithCityNameGivenAndParentIndex() {
        //given
        int index = 0;
        given(viewManager.getLayoutIndex(weatherPaneController.parent)).willReturn(index);

        //when
        weatherPaneController.deleteCityAction();

        //then
        verify(persistence).setCityName(null, index);
    }

    @Test
    void shouldSetControlsOnDeleteAction() {
        //given
        doNothing().when(weatherPaneController).saveToPersistence(any());

        //when
        weatherPaneController.deleteCityAction();

        //then
        assertThat(weatherPaneController.getDeleteCityButton().isVisible(), equalTo(false));
        assertThat(weatherPaneController.getWeatherTabPane().isVisible(), equalTo(false));
        assertThat(weatherPaneController.getDailyForecastVBox().getChildren(), empty());
        assertThat(weatherPaneController.getCurrentWeatherTab().getContent(), nullValue());
        assertThat(weatherPaneController.getTypeCityTextField().isEditable(), equalTo(true));
        assertThat(weatherPaneController.getTypeCityTextField().getFont(), is(Font.font("System", FontWeight.NORMAL, 12)));
        assertThat(weatherPaneController.getTypeCityTextField().getText(), emptyString());
    }

    @Test
    void shouldSaveToPersistenceWithNullWhenCallingDeleteCityAction() {
        //given
        doNothing().when(weatherPaneController).saveToPersistence(any());

        //when
        weatherPaneController.deleteCityAction();

        //then
        verify(weatherPaneController).saveToPersistence(null);
    }

    @Test
    void shouldSetControlsOnHandleData() {
        //given
        //when
        invokeSetControlsOnHandleData();

        //then
        assertThat(weatherPaneController.getErrorLabel().getText(), emptyString());
        assertThat(weatherPaneController.getTypeCityTextField().isEditable(), equalTo(false));
        assertThat(weatherPaneController.getTypeCityTextField().getText(), equalTo("Katowice"));
        assertThat(weatherPaneController.getTypeCityTextField().getFont(), is(Font.font("System", FontWeight.BOLD, 12)));
        assertThat(weatherPaneController.getWeatherTabPane().isVisible(), equalTo(true));
        assertThat(weatherPaneController.getDeleteCityButton().isVisible(), equalTo(true));
    }

    @Test
    void shouldSetErrorLabelTextWhileLoadingData() {
        //given
        String LOADING_MESSAGE = "Wczytywanie...";
        MainDto[] weatherDataDto = generator.getWeatherDataArray();
        given(weatherData.getWeatherData(anyString())).willReturn(weatherDataDto);
        doNothing().when(weatherPaneController).handleWeatherData(weatherDataDto[0], weatherDataDto[1]);

        //when
        weatherPaneController.initializeWeatherLayout(CITY);

        //then
        assertThat(weatherPaneController.getErrorLabel().getText(), equalTo(LOADING_MESSAGE));
        assertThat(weatherPaneController.getErrorLabel().getTextFill(), equalTo(Color.BLACK));
    }

    @Test
    void shouldInitializeWeatherDataLayoutsWhenCallingHandleWeatherData() {
        //given
        MainDto[] weatherDataDto = generator.getWeatherDataArray();

        //when
        weatherPaneController.handleWeatherData(weatherDataDto[0], weatherDataDto[1]);

        //then
        verify(viewManager).initializeCurrentWeatherLayout(weatherDataDto[0], weatherPaneController.getCurrentWeatherTab());
        verify(viewManager).initializeDailyForecastLayout(weatherPaneController.getDailyForecastVBox(), weatherDataDto[1]);
        verify(weatherPaneController).saveToPersistence(weatherDataDto[1].getCityName());
    }

    @Test
    void shouldSaveToPersistenceWithNameFromDailyForecastDataWhenCallingHandleWeatherData() {
        //given
        MainDto[] weatherDataDto = generator.getWeatherDataArray();

        //when
        weatherPaneController.handleWeatherData(weatherDataDto[0], weatherDataDto[1]);

        //then
        verify(weatherPaneController).saveToPersistence(weatherDataDto[1].getCityName());
    }

    @Test
    void shouldSetErrorLabelProperlyWhenCurrentWeatherDataErrorLabelIsNotNull() {
        //given
        String message = "Error message";
        MainDto[] weatherDataDto = generator.getWeatherDataArray();
        weatherDataDto[0].setErrorMessage(message);

        //when
        weatherPaneController.handleWeatherData(weatherDataDto[0], weatherDataDto[1]);

        //then
        assertThat(weatherPaneController.getErrorLabel().getTextFill(), equalTo(Color.RED));
        assertThat(weatherPaneController.getErrorLabel().getText(), equalTo(message));
    }

    private void invokeSetControlsOnHandleData() {
        try {
            Method method = WeatherPaneController.class.getDeclaredMethod("setControlsOnHandleData", String.class);
            method.setAccessible(true);

            method.invoke(weatherPaneController, CITY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}