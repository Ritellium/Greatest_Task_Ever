package Writers;

import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonWriter extends AbstractWriter {
    private final BufferedWriter writer;

    public JsonWriter(String filename) throws IOException {
        super(filename);
        writer = new BufferedWriter(super.getFile());
    }

    @Override
    public void write(ArrayList<String> strings) throws IOException {

        writer.write("[\n\t");

        int j = 0;
        for (String it : strings) {
            HashMap<String, String> result = new HashMap<>();
            result.put("result_" + j++, it);
            JSONObject fileResults = new JSONObject(result);
            if (j != strings.size()) {
                writer.write(fileResults.toJSONString() + ",\n\t");
            }
            else {
                writer.write(fileResults.toJSONString() + "\n");
            }
        }

        writer.write("]");
        writer.close();
    }
}
