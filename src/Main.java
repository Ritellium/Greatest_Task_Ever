import Parsers.ArithmeticParse;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Необходимо реализовать консольное приложение, которое:
1)  Читает данные из входного файла; txt, xml, json. Encoded or/and Archived
2)  Обрабатывает полученную информацию;
3)  Записывает данные в выходной файл;
*/

class FiletypeException extends Throwable {
    public FiletypeException(String mess)
    {
        super(mess);
    }
}

public class Main {

    public static String TXT = ".txt";
    public static String XML = ".xml";
    public static String JSON = ".json";

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
    public static InputStreamReader isr = new InputStreamReader(System.in);
    public static BufferedReader consoleInput = new BufferedReader(isr);

    public static void main(String[] args) throws IOException {
        try {
            System.out.println("Type the name of file to read:");
            String filename = consoleInput.readLine();

            System.out.println("Type the name of file to write result into:");
            String filenameWrite = consoleInput.readLine();
            AbstractWriter writer = createWriter(filenameWrite);

            ArrayList<String> fileContent = getStringArrayFromFile(filename);

            System.out.println("File content archived, how do you want to parse it:");
            System.out.println("> '1' to use selfmade regular expression based parser");
            System.out.println("> '2' to use selfmade determinate automaton based parser");
            System.out.println("> \"other number\" to use exp4j library tools (the only working now)");

            int mode = Integer.parseInt(consoleInput.readLine());
            ArrayList<String> contentParsed = ArithmeticParse.parseStringArray(fileContent, mode);

            writer.write(contentParsed);

        } catch (IOException | XMLStreamException | FiletypeException | ParseException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
        } finally {
            consoleInput.close();
        }
    }
}