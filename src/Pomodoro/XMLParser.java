package Pomodoro;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser
{
    private File filepath;

    public XMLParser(File path){
        this.filepath = path;
    }

    public void readFile()
    {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(filepath);

            //int numCycles = doc.getElementsByTagName("preset").item(0).getTextContent();
        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
