package Readers;

public class FileTypeException extends Throwable {
    public static final String TXT = ".txt";
    public static final String XML = ".xml";
    public static final String JSON = ".json";
    public static final String ZIP = ".zip";
    public FileTypeException(String mess)
    {
        super(mess);
    }
}
