package application.model;

import java.io.*;

public class Persistence {

    private String[] cities = new String[2];
    private boolean isPersistenceLoaded;

    private String filePath = System.getProperty("user.home") + File.separator + "DoradcaPogodowy";
    private String fileName =  File.separator + "SavedCities.txt";

    public Persistence() {
        isPersistenceLoaded = false;
    }

    public void saveToPersistence() {

            File path = new File(filePath);
            if (!path.exists()) {
                if (path.mkdir()) {
                    createFile(cities);
                } else {
                    throw new IllegalArgumentException ("Cannot create directory");
                }
            } else {
                createFile(cities);
            }

    }

    public void loadFromPersistence() {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath + fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            cities = (String[]) objectInputStream.readObject();

            isPersistenceLoaded = true;

            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFileExist() {
        File file = new File(filePath + fileName);
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
            File file = new File(filePath + fileName);
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

    public void setCities(String[] cities) {
        this.cities = cities;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}