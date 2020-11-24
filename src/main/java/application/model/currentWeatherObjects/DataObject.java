package application.model.currentWeatherObjects;

import application.model.WeatherData;

public class DataObject {

    private String country_code;
    private String city_name;
    private int clouds;
    private WeatherObject weather;
    private float temp;

    public DataObject(String country_code, String city_name, int clouds, WeatherObject weather, float temp) {
        this.country_code = country_code;
        this.city_name = city_name;
        this.clouds = clouds;
        this.weather = weather;
        this.temp = temp;
    }
}
