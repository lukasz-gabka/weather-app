package application.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherData {
    public final String CURRENT_WEATHER_URL = "http://api.weatherbit.io/v2.0/current?lang=pl";
    public final String DAILY_FORECAST_URL = "http://api.weatherbit.io/v2.0/forecast/daily?lang=pl&days=5";
    private final String API_URL = "&key="; //insert your weatherbit.io api key at the end of this string
    private final String CITY_URL = "&city=";

    public void getWeatherData(String cityName, String baseUrl) {
        HttpClient client = HttpClient.newHttpClient();

        String url = baseUrl + API_URL + CITY_URL + convertSpaceToDash(cityName);

        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).header("accept", "application/json").build();

        try {
            HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(result.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String convertSpaceToDash(String text) {
        return text.replace(' ', '-');
    }
}