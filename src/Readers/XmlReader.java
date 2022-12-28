package Readers;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class XmlReader extends AbstractReader {

    private final XMLStreamReader reader;

    public XmlReader(String fileName) throws IOException, XMLStreamException {
        super(fileName);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        reader = factory.createXMLStreamReader(super.getFile());
    }
    @Override
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
