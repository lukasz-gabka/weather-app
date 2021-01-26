package application.model;

import application.model.dto.MainDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class WeatherData {

    private static final String CURRENT_WEATHER_URL = "http://api.weatherbit.io/v2.0/current?lang=pl";
    private static final String DAILY_FORECAST_URL = "http://api.weatherbit.io/v2.0/forecast/daily?lang=pl&days=6";

    private static final String API_URL = "&key="; //insert your weatherbit.io API key at the end of this string
    private static final String CITY_URL = "&city=";

    private static final String CITY_NOT_FOUND_ERROR_MESSAGE = "Nie znaleziono podanej miejscowości.";
    private static final String SERVER_ERROR_MESSAGE = "Błąd serwera. Spróbuj ponownie później.";
    private static final String UNKNOWN_ERROR_MESSAGE = "Nieznany błąd";

    private final HttpClient client = HttpClientBuilder.create().build();
    private final Sleeper sleeper = new Sleeper();

    public MainDto getData(String cityName, String baseUrl) {
        String url = baseUrl + API_URL + CITY_URL + convertSpaceToDash(cityName);
        HttpGet request = new HttpGet(url);
        JsonParser jsonParser = new JsonParser();

        try {
            HttpResponse httpResult = client.execute(request);
            String result = jsonParser.convertJSONToString(httpResult);
            MainDto data = jsonParser.convertStringToObject(result);
            data.isExist();

            return data;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return createErrorMainDto(CITY_NOT_FOUND_ERROR_MESSAGE);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return createErrorMainDto(SERVER_ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            return createErrorMainDto(UNKNOWN_ERROR_MESSAGE);
        }
    }

    public MainDto[] getWeatherData(String cityName) {
        MainDto[] weatherDataDto = new MainDto[2];

        weatherDataDto[0] = getData(cityName, CURRENT_WEATHER_URL);
        sleeper.sleep(); // weatherbit.io API free version enables only 1 request per second
        weatherDataDto[1] = getData(cityName, DAILY_FORECAST_URL);

        return weatherDataDto;
    }

    private MainDto createErrorMainDto(String errorMessage) {
        MainDto data = new MainDto();
        data.setErrorMessage(errorMessage);

        return data;
    }

    private String convertSpaceToDash(String text) {
        return text.replace(' ', '-');
    }
}