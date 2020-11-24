package application.model;

import java.io.IOException;
import application.model.JSONParsedObjects.DataJSONObject;
import application.model.JSONParsedObjects.MainJSONObject;
import application.model.JSONParsedObjects.WeatherJSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class WeatherData {
    public final String CURRENT_WEATHER_URL = "http://api.weatherbit.io/v2.0/current?lang=pl";
    public final String DAILY_FORECAST_URL = "http://api.weatherbit.io/v2.0/forecast/daily?lang=pl&days=5";
    private final String API_URL = "&key="; //insert your weatherbit.io api key at the end of this string
    private final String CITY_URL = "&city=";

    public void getWeatherData(String cityName, String baseUrl) {
        HttpClient client = HttpClientBuilder.create().build();
        String url = baseUrl + API_URL + CITY_URL + convertSpaceToDash(cityName);
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse httpResult = client.execute(request);

            String result = JSONParser.convertJSONToString(httpResult);

            MainJSONObject mainObject = JSONParser.convertStringToObject(result);
            DataJSONObject dataJSONObject = mainObject.getData().get(0);
            WeatherJSONObject weatherJSONObject = dataJSONObject.getWeather();

            System.out.println(mainObject.getCity_name());
            System.out.println(dataJSONObject.getClouds());
            System.out.println(weatherJSONObject.getDescription());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertSpaceToDash(String text) {
        return text.replace(' ', '-');
    }
}