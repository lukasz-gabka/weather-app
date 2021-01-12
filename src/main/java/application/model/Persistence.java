package application.model;

import java.io.*;

public class Persistence {

    private String[] cities = new String[2];
    private boolean isPersistenceLoaded;

    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "DoradcaPogodowy";
    private static final String FILE_NAME =  File.separator + "SavedCities.txt";

    public Persistence() {
        isPersistenceLoaded = false;
    }

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

            isPersistenceLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isFileExist() {
        File file = new File(FILE_PATH + FILE_NAME);
        return file.exists();
    }

    public boolean isPersistenceLoaded() {
        return isPersistenceLoaded;
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

    public String[] getCities() {
        return cities;
    }
}