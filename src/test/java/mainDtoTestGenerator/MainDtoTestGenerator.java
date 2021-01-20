package mainDtoTestGenerator;

import application.model.dto.MainDto;

import java.io.*;

public class MainDtoTestGenerator {

    private final JsonParser jsonParser = new JsonParser();

    public MainDto getMainDto(String path) {
        String string = getWeatherDataString(path);
        return jsonParser.convertStringToObject(string);
    }

    public String getWeatherDataString(String path) {
        String json = "";

        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            json = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}
