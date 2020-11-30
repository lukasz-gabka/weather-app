package application.model.JSONParsedObjects;

import java.util.ArrayList;

public class MainJSON {

    private ArrayList<DataJSON> data;
    private String city_name;
    private String country_code;
    private String errorMessage;

    public ArrayList<DataJSON> getData() {
        return data;
    }

    public void isExist() {
        if (this.data == null) {
            throw new NullPointerException();
        }
    }

    public String getFullCityName() {
        return city_name + ", " + country_code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
