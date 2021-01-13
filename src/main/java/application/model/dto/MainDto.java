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

    public String getCityName() {
        return city_name;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setData(List<DataDto> data) {
        this.data = data;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
