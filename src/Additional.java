import Readers.FileTypeException;
import Writers.AbstractWriter;
import Writers.JsonWriter;
import Writers.TxtWriter;
import Writers.XmlWriter;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Readers.FileTypeException.*;


public class Additional {

    public static AbstractWriter createWriter(String filename) throws IOException, XMLStreamException, FileTypeException, ParseException {
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
                throw new FileTypeException("Wrong filetype");
            }

            return writer;
        } else {
            throw new FileTypeException("Wrong filename");
        }
    }
}
