package Readers;

import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractReader {
    private final FileReader fileModel;
    AbstractReader(String filename) throws FileNotFoundException {
        fileModel = new FileReader(filename);
    }

    public FileReader getFile() {
        return fileModel;
    }

    abstract public ArrayList<String> read() throws IOException, XMLStreamException, ParseException, FileTypeException;
}
