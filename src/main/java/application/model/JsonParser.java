package application.model;

import application.model.dto.MainDto;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class JsonParser {

    private final Gson gson = new Gson();

    public String convertJSONToString(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    public MainDto convertStringToObject(String json) {
        return gson.fromJson(json, MainDto.class);
    }
}