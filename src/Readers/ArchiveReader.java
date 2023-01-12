package Readers;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Readers.FileTypeException.*;

public class ArchiveReader extends AbstractReader {

    public static BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
    private ZipFile zipFile;
    private String folderIn = "";
    private String workFile;

    ArchiveReader(String filename) throws IOException {
        super(filename);
        workFile = filename;
        zipFile = new ZipFile(workFile);
    }

    ArchiveReader(String filename, String folderName) throws IOException {
        super(filename);
        workFile = filename;
        folderIn = folderName;
        zipFile = new ZipFile(workFile);
    }

    public ArrayList<String> read() throws IOException, XMLStreamException, ParseException, FileTypeException {
        ArrayList<String> result = new ArrayList<>();
        String workFolder = folderIn + "ZipWorks/";

        if (zipFile.isEncrypted()) {
            System.out.println("File " + workFile + " is encrypted. Enter password:");
            char[] password = consoleInput.readLine().toCharArray();
            zipFile.setPassword(password);
        }

        System.out.println("The files in archive are:");
        List<FileHeader> files = zipFile.getFileHeaders();
        for (var it : files) {
            System.out.println(it.getFileName());
        }
        System.out.println("What one do you want to read:");
        String toReadFile = consoleInput.readLine();

        zipFile.extractFile(toReadFile, workFolder);
        zipFile.close();

        result = getStringArrayFromFile(workFolder + toReadFile);

        System.gc();
        File fDelete = new File(workFolder);
        File[] deletions = fDelete.listFiles();
        for (var it: deletions){
            it.delete();
        }
        fDelete.delete();

        return result;
    }

    public static ArrayList<String> getStringArrayFromFile(String filename) throws IOException, XMLStreamException, FileTypeException, ParseException {
        ArrayList<String> result;

        Pattern fileTypePattern = Pattern.compile("[.]\\w+$");
        Matcher fileTypeFinder = fileTypePattern.matcher(filename);
        if (fileTypeFinder.find()) {
            String filetype = filename.substring(fileTypeFinder.start(), fileTypeFinder.end());

            AbstractReader reader;
            if (filetype.compareTo(TXT) == 0) {
                reader = new TxtReader(filename);
                result = reader.read();
            } else if (filetype.compareTo(XML) == 0) {
                reader = new XmlReader(filename);
                result = reader.read();
            } else if (filetype.compareTo(JSON) == 0) {
                reader = new JsonReader(filename);
                result = reader.read();
            } else if (filetype.compareTo(ZIP) == 0) {
                Pattern folderPattern = Pattern.compile("/.+$");
                Matcher folderFinder = folderPattern.matcher(filename);
                if (folderFinder.find()) {
                    String folder = filename.substring(0, folderFinder.start() + 1);
                    reader = new ArchiveReader(filename, folder);
                } else {
                    reader = new ArchiveReader(filename);
                }
                result = reader.read();
            } else {
                throw new FileTypeException("Not supported filetype");
            }

            return result;
        } else {
            throw new FileTypeException("Wrong filename");
        }
    }
}
