package application.model.JSONParsedObjects;

import java.util.ArrayList;

public class MainJSONObject {

    private ArrayList<DataJSONObject> data;
    private String city_name;
    private String country_code;

    public ArrayList<DataJSONObject> getData() {
        return data;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCountry_code() {
        return country_code;
    }
}
