package Writers;

import javax.xml.stream.XMLStreamException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractWriter {
    private final FileWriter fileModel;
    public AbstractWriter(String filename) throws IOException {
        fileModel = new FileWriter(filename);
    }
    public FileWriter getFile() {
        return fileModel;
    }
    public abstract void write(ArrayList<String> strings) throws IOException, XMLStreamException;
}
