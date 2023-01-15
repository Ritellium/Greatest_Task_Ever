package Readers;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Readers.FileTypeException.*;

// Reads unzipped files. Decorator for txt, xml, json
public class CryptoReader extends AbstractReader {
    CryptoReader(String _filename) {
        super(_filename);
    }
    private static String askForKey() throws IOException {
        System.out.println("Input the decrypt key:");
        return getConsole().readLine();
    }
    public void decrypt() throws Exception {
        FileInputStream encoded = new FileInputStream(super.getFilename());
        String key = askForKey();
        Key secretKey = new SecretKeySpec(Arrays.copyOf(key.getBytes(), 16), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] inputBytes = encoded.readAllBytes();
        encoded.close();

        byte[] outputBytes = cipher.doFinal(Base64.getDecoder().decode(inputBytes));
        FileOutputStream decoded = new FileOutputStream(super.getFilename());
        decoded.write(outputBytes);
        decoded.close();
    }
    public ArrayList<String> read() throws Exception {
        ArrayList<String> result;
        Pattern fileTypePattern = Pattern.compile("[.]\\w+$");
        Matcher fileTypeFinder = fileTypePattern.matcher(super.getFilename());
        AbstractReader reader;
        if (fileTypeFinder.find()) {
            String filetype = super.getFilename().substring(fileTypeFinder.start(), fileTypeFinder.end());
            if (filetype.compareTo(TXT) == 0) {
                reader = new TxtReader(super.getFilename());
                result = reader.read();
            } else if (filetype.compareTo(XML) == 0) {
                reader = new XmlReader(super.getFilename());
                result = reader.read();
            } else if (filetype.compareTo(JSON) == 0) {
                reader = new JsonReader(super.getFilename());
                result = reader.read();
            } else {
                throw new FileTypeException("Not supported filetype");
            }
            return result;
        } else {
            throw new FileTypeException("Wrong object name (not a folder or a file)");
        }
    }
}
