package Readers;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XmlReader {

    private XMLStreamReader reader;

    public XmlReader(String fileName) throws IOException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        reader = factory.createXMLStreamReader(new FileInputStream(fileName));
    }

    public ArrayList<String> read() throws XMLStreamException {
        ArrayList<String> result = new ArrayList<>();

        while (reader.hasNext()) {
            if (reader.next() == XMLStreamConstants.CHARACTERS) {
                String current = reader.getText().replaceAll(" ", "").replaceAll("\n", "");
                if (!current.equals("")) {
                    result.add(current);
                }
            }
        }

        return result;
    }
}
