package Readers;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;

import static Readers.FileTypeException.*;

// Reads unzipped files. Decorator for txt, xml, json
public class CryptoReader {
    private String filename;
    private String filetype;
    CryptoReader(String _filename, String _filetype) {
        filename = _filename;
        filetype = _filetype;
    }
    private static String askForKey() throws IOException {
        System.out.println("Input the decrypt key:");
        return AbstractReader.getConsole().readLine();
    }
    public void decrypt() throws Exception {
        FileInputStream encoded = new FileInputStream(filename);
        String key = askForKey();
        Key secretKey = new SecretKeySpec(Arrays.copyOf(key.getBytes(), 16), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] inputBytes = encoded.readAllBytes();
        encoded.close();

        byte[] outputBytes = cipher.doFinal(inputBytes);
        filename = filename.substring(0, filename.length() - filetype.length()) + "_decrypted" + filetype;
        FileOutputStream decoded = new FileOutputStream(filename);
        decoded.write(outputBytes);
        decoded.close();
    }
    public ArrayList<String> read() throws Exception {
        ArrayList<String> result;
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
        } else {
            throw new FileTypeException("Not supported filetype");
        }
        return result;
    }
}
