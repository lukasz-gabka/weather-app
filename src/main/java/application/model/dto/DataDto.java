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

    /**
     * Date should be in format YY-MM-DD
     * While getting current weather data, the datetime field has format YY-MM-DD:HH
     * While getting daily forecast data, the datetime field has proper format
     */
    public String getDate() {
        if (datetime.contains(":")) {
            return trimDateTime();
        }
        return datetime;
    }

    public int getPres() {
        return Math.round(pres);
    }

    public int getClouds() {
        return Math.round(clouds);
    }

    public WeatherDto getWeather() {
        return weather;
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

    public String trimDateTime() {
        int colonPosition = datetime.indexOf(":");
        return datetime.substring(0, colonPosition);
    }

    public void setWind_spd(float wind_spd) {
        this.wind_spd = wind_spd;
    }

    //converts "m/s" into "km/h"
    private float convertUnit(float value) {
        return value * 3.6f;
    }
}
