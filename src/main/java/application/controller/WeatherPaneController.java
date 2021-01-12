package application.controller;

import application.model.WeatherData;
import application.model.dto.MainDto;
import application.model.Persistence;
import application.view.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherPaneController extends BaseController {

    private static final String CURRENT_WEATHER_URL = "http://api.weatherbit.io/v2.0/current?lang=pl";
    private static final String DAILY_FORECAST_URL = "http://api.weatherbit.io/v2.0/forecast/daily?lang=pl&days=6";

    private static final String LOADING_MESSAGE = "Wczytywanie...";

    private Persistence persistence;

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
        if (!typeCityTextField.getText().equals("")) {
            initializeWeatherLayout(this.typeCityTextField.getText());
        }
    }

    @FXML
    void deleteCityAction() {
        setControlsOnDelete();

        saveToPersistence(null);
    }

    public WeatherPaneController(ViewManager viewManager, String city, Persistence persistence) {
        super(viewManager, "WeatherPane.fxml");

        this.persistence = persistence;
        setControlsOnCreate();

        if (city != null) {
            initializeWeatherLayout(city);
        }
    }

    public void saveToPersistence(String cityName) {
        //Persistence persistence = new Persistence();

        int index = viewManager.getLayoutIndex(this.parent);
        persistence.setCityName(cityName, index);
    }

    private void initializeWeatherLayout(String cityName) {
        this.errorLabel.setTextFill(Color.BLACK);
        this.errorLabel.setText(LOADING_MESSAGE);

        WeatherData weatherData = new WeatherData();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            MainDto currentWeatherData = weatherData.getWeatherData(cityName, CURRENT_WEATHER_URL);
            sleep(); // weatherbit.io API free version enables only 1 request per second
            MainDto dailyForecastData = weatherData.getWeatherData(cityName, DAILY_FORECAST_URL);

            Platform.runLater(() -> {
                handleWeatherData(currentWeatherData, dailyForecastData);
            });
        });
        executorService.shutdown();
    }

    private void handleWeatherData(MainDto currentWeatherData, MainDto dailyForecastData) {
        if (currentWeatherData.getErrorMessage() == null) {
            String fullCityName = dailyForecastData.getFullCityName();

            setControlsOnHandleData(fullCityName);

            CurrentWeatherPaneController currentWeatherPaneController = new CurrentWeatherPaneController(viewManager, currentWeatherData);
            Parent currentWeatherParent = currentWeatherPaneController.getParent();
            currentWeatherTab.setContent(currentWeatherParent);

            addDailyForecastDataToLayout(viewManager, dailyForecastData);

            saveToPersistence(dailyForecastData.getCityName());
        } else {
            this.errorLabel.setTextFill(Color.RED);
            this.errorLabel.setText(currentWeatherData.getErrorMessage());
        }
    }

    private void setControlsOnDelete() {
        deleteCityButton.setVisible(false);
        weatherTabPane.setVisible(false);

        dailyForecastVBox.getChildren().clear();
        currentWeatherTab.setContent(null);

        typeCityTextField.setFont(Font.font("System", FontWeight.NORMAL, 12));
        typeCityTextField.setEditable(true);
        typeCityTextField.clear();
    }

    private void setControlsOnCreate() {
        typeCityTextField.setFocusTraversable(false);
        deleteCityButton.setFocusTraversable(false);

        deleteCityButton.setVisible(false);
        weatherTabPane.setVisible(false);
    }

    private void setControlsOnHandleData(String fullCityName) {
        errorLabel.setText("");

        typeCityTextField.setEditable(false);
        typeCityTextField.setFont(Font.font("System", FontWeight.BOLD, 12));
        typeCityTextField.setText(fullCityName);

        weatherTabPane.getSelectionModel().select(0);
        weatherTabPane.setVisible(true);

        deleteCityButton.setVisible(true);
    }

    private void addDailyForecastDataToLayout(ViewManager viewManager, MainDto dailyForecastData) {
        int tomorrowDataIndex = 1;
        int fifthDayDataIndex = dailyForecastData.getData().size() - 1;

        for (int i = tomorrowDataIndex; i <= fifthDayDataIndex; i++) {
            int weatherPaneVBoxIndex = i - 1;
            viewManager.initializeDailyForecastLayout(
                    this.dailyForecastVBox,
                    new DailyForecastPaneController(viewManager, dailyForecastData, i),
                    weatherPaneVBoxIndex
            );
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
