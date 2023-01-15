package Writers;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.IOException;
import java.util.ArrayList;


public class XmlWriter extends AbstractWriter{

    private final XMLEvent end;
    private final XMLEvent tab;
    private final XMLEventWriter writer;
    private final XMLEventFactory eventFactory;

    public XmlWriter(String filename) throws IOException, XMLStreamException {
        super(filename);
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        writer = outputFactory.createXMLEventWriter(super.getFile());

        eventFactory = XMLEventFactory.newInstance();
        end = eventFactory.createDTD("\n");
        tab = eventFactory.createDTD("\t");
    }

    @Override
    public void write(ArrayList<String> strings) throws XMLStreamException {
        StartDocument startDocument = eventFactory.createStartDocument();
        writer.add(startDocument);

        StartElement configStartElement = eventFactory.createStartElement("", "", "Results");
        writer.add(end);
        writer.add(configStartElement);
        writer.add(end);

        for(int i = 0; i < strings.size(); i++)
        {
            int number = i+1;
            StartElement sElement = eventFactory.createStartElement("", "", "result_" + number);
            writer.add(tab);
            writer.add(sElement);

            Characters characters = eventFactory.createCharacters(strings.get(i));
            writer.add(characters);

            EndElement eElement = eventFactory.createEndElement("", "", "result_" + number);
            writer.add(eElement);
            writer.add(end);
        }

        writer.add(eventFactory.createEndElement("", "", "Results"));
        writer.add(end);
        writer.add(eventFactory.createEndDocument());
        writer.close();
    }
}
