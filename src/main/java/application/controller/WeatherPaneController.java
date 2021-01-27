package application.controller;

import application.model.WeatherData;
import application.model.dto.MainDto;
import application.model.Persistence;
import application.view.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherPaneController extends BaseController {

    private static final String LOADING_MESSAGE = "Wczytywanie...";
    private static final Font FONT_NORMAL = Font.font("System", FontWeight.NORMAL, 12);
    private static final Font FONT_BOLD = Font.font("System", FontWeight.BOLD, 12);

    private final Persistence persistence;
    private final WeatherData weatherData;
    private MainDto[] weatherDataDto;

    @FXML
    private TextField typeCityTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button deleteCityButton;

    @FXML
    private TabPane weatherTabPane;

    @FXML
    private Tab currentWeatherTab;

    @FXML
    private ScrollPane weatherScrollPane;

    @FXML
    private VBox dailyForecastVBox;

    @FXML
    void typeCityAction() {
        if (isCityTextFieldNotEmpty() && isCityTextFieldEditable()) {
            initializeWeatherLayout(this.typeCityTextField.getText());
        }
    }

    @FXML
    void deleteCityAction() {
        setControlsOnDelete();

        saveToPersistence(null);
    }

    public WeatherPaneController(ViewManager viewManager, Persistence persistence, WeatherData weatherData) {
        super(viewManager, "WeatherPane.fxml");

        this.persistence = persistence;
        this.weatherData = weatherData;
        setControlsOnCreate();
    }

    public void saveToPersistence(String cityName) {
        int index = viewManager.getLayoutIndex(this.parent);
        persistence.setCityName(cityName, index);
    }

    public void initializeWeatherLayout(String cityName) {
        setErrorLabel(Color.BLACK, LOADING_MESSAGE);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            weatherDataDto = weatherData.getWeatherData(cityName);
            Platform.runLater(() -> {
                handleWeatherData(weatherDataDto[0], weatherDataDto[1]);
            });
        });
        executorService.shutdown();
    }

    private void setErrorLabel(Color color, String message) {
        this.errorLabel.setTextFill(color);
        this.errorLabel.setText(message);
    }

    private void handleWeatherData(MainDto currentWeatherData, MainDto dailyForecastData) {
        if (currentWeatherData.getErrorMessage() == null) {
            String fullCityName = dailyForecastData.getFullCityName();
            setControlsOnHandleData(fullCityName);

            viewManager.initializeCurrentWeatherLayout(currentWeatherData, currentWeatherTab);
            viewManager.initializeDailyForecastLayout(dailyForecastVBox, dailyForecastData);

            saveToPersistence(dailyForecastData.getCityName());
        } else {
            setErrorLabel(Color.RED, currentWeatherData.getErrorMessage());
        }
    }

    private void setControlsOnDelete() {
        deleteCityButton.setVisible(false);
        weatherTabPane.setVisible(false);

        dailyForecastVBox.getChildren().clear();
        currentWeatherTab.setContent(null);

        typeCityTextField.setFont(FONT_NORMAL);
        typeCityTextField.setEditable(true);
        typeCityTextField.clear();
    }

    private void setControlsOnCreate() {
        typeCityTextField.setFocusTraversable(false);
        deleteCityButton.setFocusTraversable(false);

        deleteCityButton.setVisible(false);
        weatherTabPane.setVisible(false);
    }

    private void setControlsOnHandleData(String cityName) {
        errorLabel.setText("");

        typeCityTextField.setEditable(false);
        typeCityTextField.setFont(FONT_BOLD);
        typeCityTextField.setText(cityName);

        weatherTabPane.getSelectionModel().select(0);
        weatherTabPane.setVisible(true);

        deleteCityButton.setVisible(true);
    }

    public TextField getTypeCityTextField() {
        return typeCityTextField;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Button getDeleteCityButton() {
        return deleteCityButton;
    }

    public TabPane getWeatherTabPane() {
        return weatherTabPane;
    }

    public Tab getCurrentWeatherTab() {
        return currentWeatherTab;
    }

    public VBox getDailyForecastVBox() {
        return dailyForecastVBox;
    }

    private boolean isCityTextFieldNotEmpty() {
        return !typeCityTextField.getText().equals("");
    }

    private boolean isCityTextFieldEditable() {
        return typeCityTextField.isEditable();
    }
}
