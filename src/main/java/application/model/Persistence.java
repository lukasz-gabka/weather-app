package application.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Persistence {

    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "DoradcaPogodowy";
    private static final String FILE_NAME =  File.separator + "SavedCities.txt";
    private static String[] cityName = new String[2];

    public static void saveToPersistence(String[] cityName) {
        try {
            File filePath = new File(FILE_PATH);
            filePath.mkdir();
            File file = new File(FILE_PATH + FILE_NAME);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cityName);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCityName(int index) {
        return cityName[index];
    }

    public static void setCityName(String cityName, int index) {
        Persistence.cityName[index] = cityName;
    }

    public static String[] getCityName() {
        return cityName;
    }

    public static void setCityName(String[] cityName) {
        Persistence.cityName = cityName;
    }
}
