package Readers;

import java.io.File;
import java.io.IOException;

public class FolderReader extends DirectoryReader {
    FolderReader(String folderName) throws IOException {
        super(folderName);
    }
    public String nextFile() throws IOException {
        System.out.println("The objects in folder are:");
        File folder = new File(super.getFilename());
        File[] files = folder.listFiles();
        for (var it : files) {
            System.out.println(it.getName());
        }
        return super.getFilename() + askAboutFile();
    }
    private static String askAboutFile() throws IOException {
        System.out.println("What one do you want to read:");
        return getConsole().readLine();
    }
}
