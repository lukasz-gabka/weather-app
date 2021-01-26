package application.controller;

import application.model.dto.DataDto;
import application.model.dto.MainDto;
import application.view.ViewManager;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainDtoTestGenerator.MainDtoTestGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherDataPaneControllerTest {

    //instantiation of jxfPanel is necessary to use controllers with JavaFX controls in test scope
    //without jxfPanel, ExceptionInInitializerError and IllegalStateException is thrown
    JFXPanel jfxPanel = new JFXPanel();

    WeatherDataPaneController controller;
    MainDto currentWeatherData;

    @BeforeAll
    void generateWeatherObjects() {
        MainDtoTestGenerator generator = new MainDtoTestGenerator();
        currentWeatherData = generator.getCurrentWeatherData();

        controller = new WeatherDataPaneController(new ViewManager(), currentWeatherData, 0);
    }

    @Test
    void shouldRenderProperWeatherIcon() {
        //given
        ImageView imageView = controller.getWeatherImageView();
        Image icon = imageView.getImage();
        String imagePath = icon.getUrl();

        //when
        //then
        assertThat(imagePath, containsString("images/icons/c02n.png"));
    }

    @Test
    void temperatureLabelShouldContainProperUnitSymbol() {
        //given
        String temperature = controller.getTemperatureLabel().getText();

        //when
        //then
        assertThat(temperature, containsString("\u00B0C"));
    }

    @Test
    void pressureLabelShouldContainProperUnitSymbol() {
        //given
        String pressure = controller.getPressureLabel().getText();

        //when
        //then
        assertThat(pressure, containsString("hPa"));
    }

    @Test
    void cloudsLabelShouldContainPercentSign() {
        //given
        String clouds = controller.getCloudsLabel().getText();

        //when
        //then
        assertThat(clouds, containsString("%"));
    }

    @Test
    void humidityLabelShouldContainPercentSign() {
        //given
        String humidity = controller.getHumidityLabel().getText();

        //when
        //then
        assertThat(humidity, containsString("%"));
    }

    @Test
    void windSpeedLabelShouldContainProperUnitSymbol() {
        //given
        String windSpeed = controller.getWindSpeedLabel().getText();

        //when
        //then
        assertThat(windSpeed, containsString("km/h"));
    }

    @Test
    void arrowImageViewShouldHaveProperRotationAngle() {
        //given
        ImageView imageView = controller.getArrowImageView();
        double angleFromImageView = imageView.getRotate();

        DataDto dataDto = currentWeatherData.getData().get(0);
        double angleFromDto = dataDto.getWindDir();

        //when
        //then
        assertThat(angleFromImageView, equalTo(angleFromDto));
    }
}