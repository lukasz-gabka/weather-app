package application.model;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersistenceTest {

    private final String FILE_PATH = System.getProperty("user.dir") + File.separator + "testDir";
    private final String FILE_NAME = File.separator + "testFile.txt";

    private final Persistence persistence = new Persistence();
    private final File path = new File(FILE_PATH);
    private final File file = new File(FILE_PATH + FILE_NAME);

    String[] cities = {"Berlin", "Paris"};

    @BeforeEach
    void setup() {
        persistence.setPath(path);
        persistence.setFile(file);
        createPath(path);
    }

    @AfterEach
    void tearDown() {
        deleteDirectoryWithFile();
    }

    @Test
    void shouldReturnFalseIfFileDoesNotExist() {
        //given
        //when
        boolean isFileExist = persistence.isFileExist();

        //then
        assertThat(isFileExist, equalTo(false));
    }

    @Test
    void shouldCreateNewFileIfFileAndPathDoNotExist() throws IOException, ClassNotFoundException {
        //given
        persistence.setCities(cities);

        //when
        persistence.saveToPersistence();
        String[] citiesFromFile = readFile();

        //then
        assertThat(citiesFromFile, equalTo(cities));
    }

    @Test
    void shouldThrowAnExceptionIfCreatingPathFails() {
        //given
        persistence.setPath(new File("/#%@$@^!?:"));
        persistence.setCities(cities);

        //when
        //then
        assertThrows(IllegalArgumentException.class, persistence::saveToPersistence);
    }

    @Test
    void shouldCreateNewFileIfPathExists() throws IOException, ClassNotFoundException {
        //given
        createPath(path);
        persistence.setCities(cities);

        //when
        persistence.saveToPersistence();
        String[] citiesFromFile = readFile();

        //then
        assertThat(citiesFromFile, equalTo(cities));
    }

    @Test
    void shouldLoadFromPersistence() throws IOException {
        //given
        createFile();

        //when
        persistence.loadFromPersistence();
        String[] citiesFromFile = persistence.getCities();

        //then
        assertThat(citiesFromFile, equalTo(cities));
    }

    @Test
    void shouldReturnTrueIfFileExists() throws IOException {
        //given
        createFile();

        //when
        boolean isFileExist = persistence.isFileExist();

        //then
        assertThat(isFileExist, equalTo(true));
    }

    @Test
    void shouldAddCityToCitiesArrayByGivenIndex() {
        //given
        String city1 = cities[0];
        String city2 = cities[1];

        //when
        persistence.setCityName(city1, 0);
        persistence.setCityName(city2, 1);

        //then
        assertThat(persistence.getCities()[0], equalTo(city1));
        assertThat(persistence.getCities()[1], equalTo(city2));

    }

    private String[] readFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String[] citiesFromFile = (String[]) objectInputStream.readObject();

        fileInputStream.close();
        objectInputStream.close();

        return citiesFromFile;
    }

    private void createFile() throws IOException {
        File filePath = new File(FILE_PATH);
        filePath.mkdir();

        File file = new File(FILE_PATH + FILE_NAME);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(cities);

        objectOutputStream.close();
        fileOutputStream.close();
    }

    private void delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    private void deleteDirectoryWithFile() {
        delete(FILE_PATH + FILE_NAME);
        delete(FILE_PATH);
    }

    private void createPath(File path) {
        //File path = persistence.getPath();
        path.mkdir();
    }
}