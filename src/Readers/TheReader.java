package Readers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Readers.FileTypeException.FOLDER;
import static Readers.FileTypeException.ZIP;

// Reads everything. Decorator for zip/encrypted
public class TheReader {
    private static final String helpFolder = "Program_Inputs/ZipWorks/";
    private String filename;
    public TheReader(String _filename) throws IOException {
        filename = _filename;
        clearZipWorks(helpFolder);
    }
    public ArrayList<String> read() throws Exception {
        ArrayList<String> result;
        DirectoryReader directoryReader;
        Pattern objectTypePattern = Pattern.compile("([.]\\w+$)|(/$)");
        Matcher objectTypeFinder;
        do {
            objectTypeFinder = objectTypePattern.matcher(filename);
            if (objectTypeFinder.find()) {
                String filetype = filename.substring(objectTypeFinder.start(), objectTypeFinder.end());
                if (filetype.equals(ZIP)) {
                    Pattern folderPattern = Pattern.compile("/.+$");
                    Matcher folderFinder = folderPattern.matcher(filename);
                    if (folderFinder.find()) {
                        String folder = filename.substring(0, folderFinder.start() + 1);
                        directoryReader = new ArchiveReader(filename, folder);
                    } else {
                        directoryReader = new ArchiveReader(filename);
                    }
                } else if (filetype.equals(FOLDER)) {
                    directoryReader = new FolderReader(filename);
                } else {
                    break;
                }
                filename = directoryReader.nextFile();
            } else {
                throw new FileTypeException("Wrong object name (not a folder or a file)");
            }
        } while (true);

        CryptoReader cryptoReader = new CryptoReader(filename);
        if (askAboutDecryption()) {
           cryptoReader.decrypt();
        }
        result = cryptoReader.read();

        clearZipWorks(helpFolder);
        return result;
    }

    private static void clearZipWorks(String zipWorksFolder) throws IOException {
        System.gc();
        File fDelete = new File(zipWorksFolder);
        if (fDelete.exists()) {
            File[] deletions = fDelete.listFiles();
            assert deletions != null;
            for (var it : deletions) {
                if (it.isFile()) {
                    if (!it.delete()){
                        throw new IOException("Cannot clear ZipWorks/ folder(s)");
                    }
                } else {
                    clearZipWorks(zipWorksFolder + it.getName());
                }
            }
            if (!fDelete.delete()) {
                throw new IOException("Cannot clear ZipWorks/ folder(s)");
            }
        }
    }

    private static boolean askAboutDecryption() throws IOException {
        System.out.println("Is your file encrypted?");
        System.out.println("> '1' to yes. Other to no");
        System.out.println("* Decrypting not encrypted file will cause harm!");
        int mode = Integer.parseInt(AbstractReader.getConsole().readLine());
        return mode == 1;
    }
}