package Readers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public abstract class AbstractReader {
    private static final BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
    private final String filename;
    AbstractReader(String _filename) {
        filename = _filename;
    }
    public String getFilename() {
        return filename;
    }
    public static BufferedReader getConsole() {
        return consoleInput;
    }
    abstract public ArrayList<String> read() throws Exception;
}
