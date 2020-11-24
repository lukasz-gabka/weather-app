package application.model.JSONParsedObjects;

public class DataJSONObject {

    private float pres;
    private String country_code;
    private String city_name;
    private int clouds;
    private WeatherJSONObject weather;
    private float temp;

    public float getPres() {
        return pres;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public int getClouds() {
        return clouds;
    }

    public WeatherJSONObject getWeather() {
        return weather;
    }

    public float getTemp() {
        return temp;
    }
}
