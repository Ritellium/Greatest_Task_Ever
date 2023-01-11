package Writers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TxtWriter extends AbstractWriter {
    private final BufferedWriter writer;
    public TxtWriter(String filename) throws IOException {
        super(filename);
        writer = new BufferedWriter(super.getFile());
    }

    @Override
    public void write(ArrayList<String> strings) throws IOException {
        for(String it: strings)
        {
            writer.write(it);
            writer.newLine();
        }
        writer.close();
    }
}
