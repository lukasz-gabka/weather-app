package application.model;

import application.controller.DefaultPaneController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistence {

    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "DoradcaPogodowy";
    private static final String FILE_NAME =  File.separator + "SavedCities.txt";
    private static String[] citiesNames = new String[2];

    private static List<DefaultPaneController> defaultPaneController = new ArrayList<>();

    public void saveToPersistence(String[] cityName) {
        try {
            File filePath = new File(FILE_PATH);
            if (!filePath.exists()) {
                if (filePath.mkdir()) {
                    createFile(cityName);
                }
            } else {
                createFile(cityName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromPersistence() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            citiesNames = (String[]) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeWithPersistence() {
        for (int i = 0; i < citiesNames.length; i++) {
            if (citiesNames[i] != null) {
                DefaultPaneController controller = defaultPaneController.get(i);

                controller.initializeWeatherLayout(citiesNames[i]);
            }
        }
    }

    public static boolean isFileExists() {
        File file = new File(FILE_PATH + FILE_NAME);
        return file.exists();
    }

    private void createFile(String[] cityName) {
        try {
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

    public static void addController(DefaultPaneController controller) {
        defaultPaneController.add(controller);
    }

    public void setCityName(String cityName, int index) {
        citiesNames[index] = cityName;
    }

    public static String[] getCityName() {
        return citiesNames;
    }
}