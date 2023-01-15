package Readers;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;

import java.io.IOException;
import java.util.List;

public class ArchiveReader extends DirectoryReader {
    private final ZipFile zipFile;
    private String folderIn = "";
    ArchiveReader(String filename) {
        super(filename);
        zipFile = new ZipFile(super.getFilename());
    }
    ArchiveReader(String filename, String folderName) {
        super(filename);
        folderIn = folderName;
        zipFile = new ZipFile(super.getFilename());
    }
    public String nextFile() throws IOException {
        String workFolder = folderIn + "ZipWorks/";

        if (zipFile.isEncrypted()) {
            zipFile.setPassword(askAboutKey());
        }

        System.out.println("The files in archive are:");
        List<FileHeader> files = zipFile.getFileHeaders();
        for (var it : files) {
            System.out.println(it.getFileName());
        }
        String toReadFile = askAboutFile();

        zipFile.extractFile(toReadFile, workFolder);
        zipFile.close();
        return workFolder + toReadFile;
    }

    private static String askAboutFile() throws IOException {
        System.out.println("What one do you want to read:");
        return getConsole().readLine();
    }

    private char[] askAboutKey() throws IOException {
        System.out.println("File " + super.getFilename() + " is encrypted. Enter password:");
        return getConsole().readLine().toCharArray();
    }
}
