package Readers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReader {

    private JSONArray reader;

    public JsonReader(String fileName) throws IOException, ParseException {
        reader = (JSONArray) new JSONParser().parse(new FileReader(fileName));
    }

    public ArrayList<String> read() {

        ArrayList<String> result = new ArrayList<>();

        JSONObject current = (JSONObject) reader.get(0);
        for (int j = 0; j < current.size(); j++) {

            if (current.get("expression"+ (j + 1)) != null) {
                result.add(current.get("expression" + (j + 1)).toString());
            }
        }

        return result;
    }
}
