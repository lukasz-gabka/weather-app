package application.model.JSONParsedObjects;

import java.util.ArrayList;

public class MainJSONObject {

    private ArrayList<DataJSONObject> data;
    private String city_name;
    private String country_code;
    private String errorMessage;

    public ArrayList<DataJSONObject> getData() {
        return data;
    }

    public void isExist() {
        if (this.data == null) {
            throw new NullPointerException();
        }
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
