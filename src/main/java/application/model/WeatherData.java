package application.model;

import application.model.JSONParsedObjects.MainJSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class WeatherData {
    public final String CURRENT_WEATHER_URL = "http://api.weatherbit.io/v2.0/current?lang=pl";
    public final String DAILY_FORECAST_URL = "http://api.weatherbit.io/v2.0/forecast/daily?lang=pl&days=6";
    private final String API_URL = "&key="; //insert your weatherbit.io api key at the end of this string
    private final String CITY_URL = "&city=";

    public MainJSON getWeatherData(String cityName, String baseUrl) {
        HttpClient client = HttpClientBuilder.create().build();
        String url = baseUrl + API_URL + CITY_URL + convertSpaceToDash(cityName);
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse httpResult = client.execute(request);

            String result = JSONParser.convertJSONToString(httpResult);

            MainJSON data = JSONParser.convertStringToObject(result);
            data.isExist();

            return data;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            MainJSON data = new MainJSON();
            data.setErrorMessage("Nie znaleziono podanej miejscowości.");

            return data;
        } catch (NullPointerException e) {
            e.printStackTrace();

            MainJSON data = new MainJSON();
            data.setErrorMessage("Błąd serwera. Spróbuj ponownie później.");

            return data;
        } catch (Exception e) {
            e.printStackTrace();

            MainJSON data = new MainJSON();
            data.setErrorMessage("Nieznany błąd");

            return data;
        }
    }

    private String convertSpaceToDash(String text) {
        return text.replace(' ', '-');
    }
}