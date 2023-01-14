import Readers.ArchiveReader;
import Readers.FileTypeException;
import Writers.AbstractWriter;
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

public class Main {
    public static InputStreamReader isr = new InputStreamReader(System.in);
    public static BufferedReader consoleInput = new BufferedReader(isr);

    public static void main(String[] args) throws IOException {
        try {
            System.out.println("Type the name of file to read:");
            String filename = "Program_Inputs/" + consoleInput.readLine();

            System.out.println("Type the name of file to write result into:");
            String filenameWrite =  "Program_Results/" + consoleInput.readLine();
            AbstractWriter writer = Additional.createWriter(filenameWrite);

            ArrayList<String> fileContent = ArchiveReader.getStringArrayFromFile(filename); //

            System.out.println("File content archived, how do you want to parse it:");
            System.out.println("> '1' to use selfmade regular expression based parser");
            System.out.println("* Expression should not contain brackets");
            System.out.println("> '2' to use selfmade functions based parser");
            System.out.println("* Any brackets level supported");
            System.out.println("> \"other number\" to use exp4j library tools (works)");

            int mode = Integer.parseInt(consoleInput.readLine());
            ArrayList<String> contentParsed = Additional.parseStringArray(fileContent, mode);

            writer.write(contentParsed);

        } catch (IOException | XMLStreamException | FileTypeException | ParseException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
        } finally {
            consoleInput.close();
        }
    }
}