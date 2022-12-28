import Readers.AbstractReader;
import Readers.JsonReader;
import Readers.TxtReader;
import Readers.XmlReader;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

        if(filename.length() < 5)
        {
            throw new FiletypeException("Filename can not be that small");
        }

        String filetype = filename.substring(filename.length() - 5);
        String filetypeShort = filetype.substring(1);

        AbstractReader reader;
        if (filetypeShort.compareTo(TXT) == 0) {
            reader = new TxtReader(filename);
            result = reader.read();
        }
        else if (filetypeShort.compareTo(XML) == 0) {
            reader = new XmlReader(filename);
            result = reader.read();
        }
        else if (filetype.compareTo(JSON) == 0) {
            reader = new JsonReader(filename);
            result = reader.read();
        }
        else
        {
            throw new FiletypeException("Wrong filetype");
        }

        return result;
    } // Should be in zip reader

    public static InputStreamReader isr=new InputStreamReader(System.in);
    public static BufferedReader consoleInput=new BufferedReader(isr);

    public static void main(String[] args)  throws IOException
    {
        try{
            System.out.println("Type the name of file to read:");

            String filename = consoleInput.readLine();
            ArrayList<String> fileContent = new ArrayList<>(getStringArrayFromFile(filename));

            for (String it: fileContent)
            {
                System.out.println("Content: " + it);
            } ///
        }
        catch (IOException | XMLStreamException | FiletypeException | ParseException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
        }
        finally {
            consoleInput.close();
        }
    }
}