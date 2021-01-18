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
    private float rh;
    private String ob_time;

    public int getPres() {
        return Math.round(pres);
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
        return Math.round(convertUnit(wind_spd));
    }

    public double getWindDir() {
        return Math.round(wind_dir);
    }

    public int getRh() {
        return Math.round(rh);
    }

    public String getObTime() {
        int spacePosition = ob_time.indexOf(" ");
        return ob_time.substring(0, spacePosition);
    }
    public void setWind_spd(float wind_spd) {
        this.wind_spd = wind_spd;
    }

    //converts "m/s" into "km/h"
    private float convertUnit(float value) {
        return value * 3.6f;
    }
}
