package application.model;

import application.model.JSONParsedObjects.MainJSON;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class JSONParser {

    public static String convertJSONToString(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    public static MainJSON convertStringToObject(String json) {
        Gson gson = new Gson();

        return gson.fromJson(json, MainJSON.class);
    }
}