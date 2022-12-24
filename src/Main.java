import java.io.*;
import java.util.ArrayList;

/*
Необходимо реализовать консольное приложение, которое:
1)  Читает данные из входного файла; txt, xml, json. Encoded or/and Archived
2)  Обрабатывает полученную информацию;
3)  Записывает данные в выходной файл;
*/

public class Main {

    public static void main(String[] args)  throws IOException
    {
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader consoleInput=new BufferedReader(isr);

        FileReader fileIn = new FileReader("input.txt");
        BufferedReader reader = new BufferedReader(fileIn);

        FileWriter fileOut = new FileWriter("output.txt");
        BufferedWriter writer = new BufferedWriter(fileOut);

        try {
            System.out.println("Enter filename of input file (.txt | .xml | .json are supported)");
            String inputFile = consoleInput.readLine();
            System.out.println("Enter filename of output file (.txt | .xml | .json are supported)");
            String outputFile = consoleInput.readLine();

            String xml_txtCheckIn = inputFile.substring(inputFile.length()-4);
            String jsonCheckIn = inputFile.substring(inputFile.length()-5);
            if (xml_txtCheckIn != ".xml" && xml_txtCheckIn != ".txt" && jsonCheckIn != ".json")
            {
                System.out.println("Wrong input file name");
                throw new IOException();
            }

            String xml_txtCheckOut = outputFile.substring(outputFile.length()-4);
            String jsonCheckOut = outputFile.substring(outputFile.length()-5);
            if (xml_txtCheckIn != ".xml" && xml_txtCheckIn != ".txt" && jsonCheckIn != ".json")
            {
                System.out.println("Wrong output file name");
                throw new IOException();
            }

            fileIn = new FileReader(inputFile);
            reader = new BufferedReader(fileIn);

            fileOut = new FileWriter(outputFile);
            writer = new BufferedWriter(fileOut);

            ArrayList<String> fileText = new ArrayList<String>();



        }
        catch (IOException e) {
            System.out.println("Ошибка чтения из файла");
        }
        finally {
            writer.close();
            reader.close();
        }
    }
}