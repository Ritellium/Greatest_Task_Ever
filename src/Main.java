import Parsers.TheParser;
import Readers.AbstractReader;
import Readers.TheReader;
import Writers.TheWriter;

import java.io.IOException;
import java.util.ArrayList;

/*
Необходимо реализовать консольное приложение, которое:
1)  Читает данные из входного файла; txt, xml, json. Encoded or/and Archived
2)  Обрабатывает полученную информацию;
3)  Записывает данные в выходной файл;
*/

public class Main {
    private static String askInputFile() throws IOException {
        System.out.println("Type the name of file to read:");
        return "Program_Inputs/" + AbstractReader.getConsole().readLine();
    }
    private static String askOutputFile() throws IOException {
        System.out.println("Type the name of file to write result into:");
        return "Program_Results/" + AbstractReader.getConsole().readLine();
    }
    private static int askParsingMode() throws IOException {
        System.out.println("File content archived, how do you want to parse it:");
        System.out.println("> '1' to use selfmade regular expression based parser");
        System.out.println("* Expression should not contain brackets");
        System.out.println("> '2' to use selfmade functions based parser");
        System.out.println("* Any brackets level supported");
        System.out.println("> \"other number\" to use exp4j library tools (works)");
        return Integer.parseInt(AbstractReader.getConsole().readLine());
    }
    public static void main(String[] args) throws IOException {
        try {
            String filenameRead =  askInputFile();
            TheReader reader = new TheReader(filenameRead);
            ArrayList<String> fileContent = reader.read();

            String filenameWrite = askOutputFile();
            TheWriter writer = new TheWriter(filenameWrite);

            int mode = askParsingMode();
            ArrayList<String> contentParsed = TheParser.parseStringArray(fileContent, mode);

            writer.write(contentParsed);
            System.out.println("Results in " + filenameWrite);

        } catch (Exception e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
        } finally {
            AbstractReader.getConsole().close();
        }
    }
}