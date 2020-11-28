package application.model.JSONParsedObjects;

public class DataJSONObject {

    private float pres;
    private String country_code;
    private String city_name;
    private float clouds;
    private WeatherJSONObject weather;
    private String datetime;
    private float temp;
    private float wind_spd;
    private double wind_dir;
    private float app_temp;
    private float rh;

    public int getPres() {
        return roundFloatToInt(pres);
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public int getClouds() {
        return Math.round(clouds);
    }

    public WeatherJSONObject getWeather() {
        return weather;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getTemp() {
        return Math.round(temp);
    }

    public int getWind_spd() {
        //converts "m/s" into "km/h"
        return Math.round(wind_spd * 3.6f);
    }

    public double getWind_dir() {
        return Math.round(wind_dir);
    }

    public int getApp_temp() {
        return Math.round(app_temp);
    }

    public int getRh() {
        return Math.round(rh);
    }

    private int roundFloatToInt(float number) {
        return Math.round(number);
    }
}
