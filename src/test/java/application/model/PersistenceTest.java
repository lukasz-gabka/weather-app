package application.model;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersistenceTest {

    private final String FILE_PATH = System.getProperty("user.dir") + File.separator + "testDir";
    private final String FILE_NAME = File.separator + "testFile.txt";

    Persistence persistence = new Persistence();

    String[] cities = {"Berlin", "Paris"};

    @BeforeAll
     void changeVariablesForTest() {
        persistence.setFilePath(FILE_PATH);
        persistence.setFileName(FILE_NAME);
    }

    @AfterEach
    void clean() {
        deleteDirectoryWithFile();
        persistence.setCities(null);
    }

    @Test
    void shouldCreateNewFile() throws IOException, ClassNotFoundException {
        //given
        persistence.setCities(cities);

        //when
        persistence.saveToPersistence();

        //then
        String[] citiesFromFile = readFile();

        assertThat(citiesFromFile, equalTo(cities));
    }

    @Test
    void shouldLoadFromPersistence() throws IOException {
        //given
        createFile();

        //when
        persistence.loadFromPersistence();

        //then
        String[] citiesFromFile = persistence.getCities();

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
    void shouldReturnFalseIfFileDoesNotExist() throws IOException {
        //given
        //when
        boolean isFileExist = persistence.isFileExist();

        //then
        assertThat(isFileExist, equalTo(false));
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
        FileInputStream fileInputStream = new FileInputStream(persistence.getFilePath() + persistence.getFileName());
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
        delete(persistence.getFilePath() + persistence.getFileName());
        delete(persistence.getFilePath());
    }
}