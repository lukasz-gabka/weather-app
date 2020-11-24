package application.model;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

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
            HttpEntity entity = httpResult.getEntity();
            String result = EntityUtils.toString(entity);

            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String convertSpaceToDash(String text) {
        return text.replace(' ', '-');
    }
}