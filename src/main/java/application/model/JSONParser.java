package application.model;

import application.model.currentWeatherObjects.MainObject;
import com.google.gson.Gson;

public class JSONParser {
    public static MainObject parseCurrentWeather(String json) {
        Gson gson = new Gson();

        return gson.fromJson(json, MainObject.class);
    }

}