package Readers;

public class FileTypeException extends Exception {
    public static final String TXT = ".txt";
    public static final String XML = ".xml";
    public static final String JSON = ".json";
    public static final String ZIP = ".zip";
    public static final String FOLDER = "/";
    public FileTypeException(String mess)
    {
        super(mess);
    }
}
