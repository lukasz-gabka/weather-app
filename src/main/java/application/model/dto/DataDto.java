package application.model.dto;

public class DataDto {

    private float pres;
    private String country_code;
    private String city_name;
    private float clouds;
    private WeatherDto weather;
    private String datetime;
    private float temp;
    private float wind_spd;
    private double wind_dir;
    private float app_temp;
    private float rh;

    public int getPres() {
        return roundFloatToInt(pres);
    }

    public String getCountryCode() {
        return country_code;
    }

    public String getCityName() {
        return city_name;
    }

    public int getClouds() {
        return Math.round(clouds);
    }

    public WeatherDto getWeather() {
        return weather;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getTemp() {
        return Math.round(temp);
    }

    public int getWindSpd() {
        return Math.round(wind_spd * 3.6f); //converts "m/s" into "km/h"
    }

    public double getWindDir() {
        return Math.round(wind_dir);
    }

    public int getAppTemp() {
        return Math.round(app_temp);
    }

    public int getRh() {
        return Math.round(rh);
    }

    private int roundFloatToInt(float number) {
        return Math.round(number);
    }
}