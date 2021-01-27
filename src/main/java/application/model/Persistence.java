package application.model;

import java.io.*;

public class Persistence {

    private String[] cities = new String[2];
    private boolean isPersistenceLoaded;

    private File path;
    private File file;

    public Persistence() {
        String filePath = System.getProperty("user.home") + File.separator + "DoradcaPogodowy";
        String fileName = File.separator + "SavedCities.txt";

        path = new File(filePath);
        file = new File(filePath + fileName);
        isPersistenceLoaded = false;
    }

    public void saveToPersistence() {
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
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            cities = (String[]) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
            isPersistenceLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFileExist() {
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

    public File getPath() {
        return path;
    }

    public void setCities(String[] cities) {
        this.cities = cities;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public void setFile(File file) {
        this.file = file;
    }
}