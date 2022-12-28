package Readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TxtReader {

    private BufferedReader reader;

    public TxtReader(String fileName) throws FileNotFoundException
    {
        reader = new BufferedReader(new FileReader(fileName));
    }

    public ArrayList<String> read() throws IOException {
        ArrayList<String> result = new ArrayList<>();

        Stream<String> fileStrings = reader.lines();
        fileStrings.forEach(str->result.add(str));

        reader.close();

        return result;
    }
}
