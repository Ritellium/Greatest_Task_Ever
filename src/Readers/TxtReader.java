package Readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TxtReader extends AbstractReader {
    private final BufferedReader reader;
    public TxtReader(String fileName) throws FileNotFoundException
    {
        super(fileName);
        reader = new BufferedReader(new FileReader(super.getFilename()));
    }
    @Override
    public ArrayList<String> read() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        Stream<String> fileStrings = reader.lines();
        fileStrings.forEach(result::add);

        reader.close();
        return result;
    }
}
