package application.model;

import application.model.dto.MainDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class WeatherData {

    private static final String API_URL = "&key="; //insert your weatherbit.io API key at the end of this string
    private static final String CITY_URL = "&city=";

    private static final String CITY_NOT_FOUND_ERROR_MESSAGE = "Nie znaleziono podanej miejscowości.";
    private static final String SERVER_ERROR_MESSAGE = "Błąd serwera. Spróbuj ponownie później.";
    private static final String UNKNOWN_ERROR_MESSAGE = "Nieznany błąd";

    private final HttpClient client = HttpClientBuilder.create().build();

    public MainDto getWeatherData(String cityName, String baseUrl) {
        String url = baseUrl + API_URL + CITY_URL + convertSpaceToDash(cityName);
        HttpGet request = new HttpGet(url);
        JSONParser jsonParser = new JSONParser();

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

    private MainDto createErrorMainDto(String errorMessage) {
        MainDto data = new MainDto();
        data.setErrorMessage(errorMessage);

        return data;
    }

    private String convertSpaceToDash(String text) {
        return text.replace(' ', '-');
    }
}