import Readers.AbstractReader;
import Readers.JsonReader;
import Readers.TxtReader;
import Readers.XmlReader;
import Writers.AbstractWriter;
import Writers.JsonWriter;
import Writers.TxtWriter;
import Writers.XmlWriter;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FiletypeException extends Throwable {
    public FiletypeException(String mess)
    {
        super(mess);
    }
}

public class Additional {
    public static final String TXT = ".txt";
    public static final String XML = ".xml";
    public static final String JSON = ".json";

    public static ArrayList<String> getStringArrayFromFile(String filename) throws IOException, XMLStreamException, FiletypeException, ParseException {
        ArrayList<String> result;

        Pattern fileTypePattern = Pattern.compile("[.]\\w+$");
        Matcher fileTypeFinder = fileTypePattern.matcher(filename);
        if (fileTypeFinder.find()) {
            String filetype = filename.substring(fileTypeFinder.start(), fileTypeFinder.end());

            AbstractReader reader;
            if (filetype.compareTo(TXT) == 0) {
                reader = new TxtReader(filename);
                result = reader.read();
            } else if (filetype.compareTo(XML) == 0) {
                reader = new XmlReader(filename);
                result = reader.read();
            } else if (filetype.compareTo(JSON) == 0) {
                reader = new JsonReader(filename);
                result = reader.read();
            } else {
                throw new FiletypeException("Wrong filetype");
            }

            return result;
        } else {
            throw new FiletypeException("Wrong filename");
        }
    } // Should be in zip reader

    public static AbstractWriter createWriter(String filename) throws IOException, XMLStreamException, FiletypeException, ParseException {
        Pattern fileTypePattern = Pattern.compile("[.]\\w+$");
        Matcher fileTypeFinder = fileTypePattern.matcher(filename);
        if (fileTypeFinder.find()) {
            String filetype = filename.substring(fileTypeFinder.start(), fileTypeFinder.end());

            AbstractWriter writer;
            if (filetype.compareTo(TXT) == 0) {
                writer = new TxtWriter(filename);
            } else if (filetype.compareTo(XML) == 0) {
                writer = new XmlWriter(filename);
            } else if (filetype.compareTo(JSON) == 0) {
                writer = new JsonWriter(filename);
            } else {
                throw new FiletypeException("Wrong filetype");
            }

            return writer;
        } else {
            throw new FiletypeException("Wrong filename");
        }
    }
}
