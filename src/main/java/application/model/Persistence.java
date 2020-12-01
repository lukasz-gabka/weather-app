package application.model;

public class Persistence {

    private static String[] cityName = new String[2];

    public static String getCityName(int index) {
        return cityName[index];
    }

    public static void setCityName(String cityName, int index) {
        Persistence.cityName[index] = cityName;
    }
}
