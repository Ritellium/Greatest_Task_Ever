package Writers;

import Readers.AbstractReader;
import Readers.FileTypeException;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Readers.FileTypeException.*;

// Decorator for other Writers. Can encode and archive (with password too)
public final class TheWriter {
    private final String filename;
    public TheWriter(String _filename) {
        filename = _filename;
    }
    private String askAboutEncrypt() throws IOException {
        System.out.println("Do you want to encode file?");
        System.out.println("> Input password. Press enter to skip:");
        return AbstractReader.getConsole().readLine();
    }
    private boolean askAboutZip() throws IOException {
        System.out.println("Do you want to zip file into archive?");
        System.out.println("> '1' to yes. Other to no");
        int mode = Integer.parseInt(AbstractReader.getConsole().readLine());
        return mode == 1;
    }
    private String askPassword() throws IOException {
        System.out.println("Input zip password. Press enter to skip");
        return AbstractReader.getConsole().readLine();
    }
    public void write(ArrayList<String> strings) throws Exception {
        Pattern fileTypePattern = Pattern.compile("[.]\\w+$");
        Matcher fileTypeFinder = fileTypePattern.matcher(filename);
        AbstractWriter writer;
        if (fileTypeFinder.find()) {
            String filetype = filename.substring(fileTypeFinder.start(), fileTypeFinder.end());
            if (filetype.compareTo(TXT) == 0) {
                writer = new TxtWriter(filename);
            } else if (filetype.compareTo(XML) == 0) {
                writer = new XmlWriter(filename);
            } else if (filetype.compareTo(JSON) == 0) {
                writer = new JsonWriter(filename);
            } else {
                throw new FileTypeException("Wrong filetype");
            }
            writer.write(strings);
        } else {
            throw new FileTypeException("Wrong filename");
        }
        encrypt(askAboutEncrypt());
        if (askAboutZip()) {
            addZip(askPassword());
        }
    }
    private void addZip(String password) throws IOException {
        File file = new File(filename);

        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        if (!password.equals("")) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(EncryptionMethod.AES);
        }

        Pattern fileTypePattern = Pattern.compile("[.]\\w+$");
        Matcher fileTypeFinder = fileTypePattern.matcher(filename);
        if (!fileTypeFinder.find()) {
            throw new ZipException("Wrong filename of file to be packed");
        }
        String filenameZip = filename.substring(0, fileTypeFinder.start()) + ZIP;

        ZipFile zipFile = new ZipFile(filenameZip);
        if (!password.equals("")) {
            zipFile.setPassword(password.toCharArray());
        }
        zipFile.addFile(file, parameters);
        zipFile.close();
    }
    private void encrypt(String key) throws Exception {
        if (!key.equals("")) {
            FileInputStream input = new FileInputStream(filename);
            Key secretKey = new SecretKeySpec(Arrays.copyOf(key.getBytes(), 16), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] inputBytes = input.readAllBytes();
            byte[] outputBytes = cipher.doFinal(inputBytes);
            input.close();
            FileOutputStream encoded = new FileOutputStream(filename);
            encoded.write(outputBytes);
            encoded.close();
        }
    }
}
