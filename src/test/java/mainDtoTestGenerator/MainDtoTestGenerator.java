package mainDtoTestGenerator;

import application.model.dto.MainDto;
import com.sun.tools.javac.Main;

import java.io.*;

public class MainDtoTestGenerator {

    private final String CURRENT_WEATHER_DATA_PATH = "src/test/resources/CurrentWeatherJson.txt";
    private final String DAILY_FORECAST_DATA_PATH = "src/test/resources/DailyForecastJson.txt";

    private final JsonParser jsonParser = new JsonParser();

    public MainDto getCurrentWeatherData() {
        return getMainDto(CURRENT_WEATHER_DATA_PATH);
    }

    public MainDto getDailyForecastData() {
        return getMainDto(DAILY_FORECAST_DATA_PATH);
    }

    public MainDto[] getWeatherDataArray() {
        MainDto[] array = new MainDto[2];
        array[0] = getMainDto(CURRENT_WEATHER_DATA_PATH);
        array[1] = getMainDto(DAILY_FORECAST_DATA_PATH);

        return array;
    }

    private MainDto getMainDto(String path) {
        String string = getWeatherDataString(path);
        return jsonParser.convertStringToObject(string);
    }

    private String getWeatherDataString(String path) {
        String json = "";

        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            json = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}
