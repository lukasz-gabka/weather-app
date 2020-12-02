package application.model;

import application.controller.DefaultPaneController;

import java.io.*;
import java.util.ArrayList;

public class Persistence {

    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "DoradcaPogodowy";
    private static final String FILE_NAME =  File.separator + "SavedCities.txt";
    private static String[] citiesNames = new String[2];

    private static ArrayList<DefaultPaneController> defaultPaneController = new ArrayList<DefaultPaneController>();

    public static void saveToPersistence(String[] cityName) {
        try {
            File filePath = new File(FILE_PATH);
            if (!isPathExists())
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

    public static void initializeWithPersistence() {
        for (int i = 0; i <= 1; i++) {
            if (citiesNames[i] != null) {
                DefaultPaneController controller = defaultPaneController.get(i);

                controller.initializeWeatherLayout(citiesNames[i]);
            }
        }
    }

    public static boolean isPathExists() {
        File file = new File(FILE_PATH);
        return file.exists();
    }

    public static boolean isFileExists() {
        File file = new File(FILE_PATH + FILE_NAME);
        return file.exists();
    }

    public static void addController(DefaultPaneController controller) {
        defaultPaneController.add(controller);
    }

    public static String getCityName(int index) {
        return citiesNames[index];
    }

    public static void setCityName(String cityName, int index) {
        Persistence.citiesNames[index] = cityName;
    }

    public static String[] getCityName() {
        return citiesNames;
    }
}