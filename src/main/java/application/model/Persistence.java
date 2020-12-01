package application.model;

import java.io.*;

public class Persistence {

    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "DoradcaPogodowy";
    private static final String FILE_NAME =  File.separator + "SavedCities.txt";
    private static String[] citiesNames = new String[2];

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

    public static void loadFromPersistence() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            String[] array = (String[]) objectInputStream.readObject();

            citiesNames = array;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCityName(int index) {
        return citiesNames[index];
    }

    public static void setCityName(String cityName, int index) {
        Persistence.citiesNames[index] = cityName;
    }

    public static String[] getCitiesNames() {
        return citiesNames;
    }
}
