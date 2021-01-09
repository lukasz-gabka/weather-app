package application.model;

import application.controller.DefaultPaneController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistence {

    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "DoradcaPogodowy";
    private static final String FILE_NAME =  File.separator + "SavedCities.txt";
    private static String[] cities = new String[2];

    private static List<DefaultPaneController> controllers = new ArrayList<>();

    public void saveToPersistence() {
        try {
            File filePath = new File(FILE_PATH);
            if (!filePath.exists()) {
                if (filePath.mkdir()) {
                    createFile(cities);
                }
            } else {
                createFile(cities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromPersistence() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            cities = (String[]) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeWithPersistence() {
        for (int i = 0; i < cities.length; i++) {
            if (cities[i] != null) {
                DefaultPaneController controller = controllers.get(i);
                controller.initializeWeatherLayout(cities[i]);
            }
        }
    }

    public static boolean isFileExists() {
        File file = new File(FILE_PATH + FILE_NAME);
        return file.exists();
    }

    public void addController(DefaultPaneController controller) {
        controllers.add(controller);
    }

    public void setCityName(String cityName, int index) {
        cities[index] = cityName;
    }

    private void createFile(String[] cities) {
        try {
            File file = new File(FILE_PATH + FILE_NAME);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cities);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}