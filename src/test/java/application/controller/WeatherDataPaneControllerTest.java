package application.controller;

import application.model.dto.DataDto;
import application.model.dto.MainDto;
import application.view.ViewManager;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainDtoTestGenerator.MainDtoTestGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

class WeatherDataPaneControllerTest {

    //instantiation of jxfPanel is necessary to use controllers with JavaFX controls in test scope
    //without jxfPanel, ExceptionInInitializerError and IllegalStateException is thrown
    private final JFXPanel jfxPanel = new JFXPanel();

    private WeatherDataPaneController controller;
    private MainDto currentWeatherData;

    @BeforeEach
    void generateWeatherObjects() {
        MainDtoTestGenerator generator = new MainDtoTestGenerator();
        currentWeatherData = generator.getCurrentWeatherData();

        controller = new WeatherDataPaneController(new ViewManager(), currentWeatherData, 0);
    }

    @Test
    void shouldRenderProperWeatherIcon() {
        //given
        //when
        ImageView imageView = controller.getWeatherImageView();

        //then
        Image icon = imageView.getImage();
        String imagePath = icon.getUrl();

        assertThat(imagePath, containsString("images/icons/c02n.png"));
    }

    @Test
    void temperatureLabelShouldContainProperUnitSymbol() {
        //given
        //when
        String temperature = controller.getTemperatureLabel().getText();

        //then
        assertThat(temperature, containsString("\u00B0C"));
    }

    @Test
    void pressureLabelShouldContainProperUnitSymbol() {
        //given
        //when
        String pressure = controller.getPressureLabel().getText();

        //then
        assertThat(pressure, containsString("hPa"));
    }

    @Test
    void cloudsLabelShouldContainPercentSign() {
        //given
        //when
        String clouds = controller.getCloudsLabel().getText();

        //then
        assertThat(clouds, containsString("%"));
    }

    @Test
    void humidityLabelShouldContainPercentSign() {
        //given
        //when
        String humidity = controller.getHumidityLabel().getText();

        //then
        assertThat(humidity, containsString("%"));
    }

    @Test
    void windSpeedLabelShouldContainProperUnitSymbol() {
        //given
        //when
        String windSpeed = controller.getWindSpeedLabel().getText();

        //then
        assertThat(windSpeed, containsString("km/h"));
    }

    @Test
    void arrowImageViewShouldHaveProperRotationAngle() {
        //given
        //when
        ImageView imageView = controller.getArrowImageView();

        //then
        double angleFromImageView = imageView.getRotate();
        DataDto dataDto = currentWeatherData.getData().get(0);
        double angleFromDto = dataDto.getWindDir();

        assertThat(angleFromImageView, equalTo(angleFromDto));
    }
}