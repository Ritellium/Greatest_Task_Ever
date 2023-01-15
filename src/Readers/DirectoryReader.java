package Readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class DirectoryReader {
    private static final BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
    private final String filename;
    DirectoryReader(String _filename) {
        filename = _filename;
    }
    public String getFilename() {
        return filename;
    }
    public static BufferedReader getConsole() {
        return consoleInput;
    }
    public abstract String nextFile() throws IOException;
}
