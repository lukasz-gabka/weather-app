package application.model.dto;

import java.util.List;

public class MainDto {

    private List<DataDto> data;
    private String city_name;
    private String country_code;
    private String errorMessage;

    public List<DataDto> getData() {
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

    public String getCityName() {
        return city_name;
    }
}
