package Pomodore;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLParser
{
    private final File presetFilepath;
    // stores the Variables of the timer on closeup and writes them back into the textfields on startup
    private final File timerConfigFilepath;

    public XMLParser(String presetFilepath, String timerConfigFilepath)
    {
        this.presetFilepath = new File(presetFilepath);
        this.timerConfigFilepath = new File(timerConfigFilepath);
    }

    public PomodoroConfig readPreset(int presetId)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(presetFilepath);

            PomodoroConfig config = new PomodoroConfig();

            // preset contains all the information of the selected Preset Button
            Element preset = (Element) doc.getElementsByTagName("preset").item(presetId);

            // Retrieve the Variables
            config.workTime = Float.parseFloat(preset.getElementsByTagName("worktime").item(0).getTextContent().trim());
            config.breakTime = Float.parseFloat(preset.getElementsByTagName("breaktime").item(0).getTextContent().trim());
            config.breakTimePomodoro = Float.parseFloat(preset.getElementsByTagName("longbreaktime").item(0).getTextContent().trim());
            config.numCycles = Integer.parseInt(preset.getElementsByTagName("numCycles").item(0).getTextContent().trim());
            config.numPomodoroCycles = Integer.parseInt(preset.getElementsByTagName("numPomodoroCycles").item(0).getTextContent().trim());

            return config;
        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void writePreset(PomodoroConfig config, int presetId)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(presetFilepath);

            // preset contains all the information of the selected Preset Button
            Element preset = (Element) doc.getElementsByTagName("preset").item(presetId);


            // Write the Variables
            preset.getElementsByTagName("worktime").item(0).setTextContent(String.format("%s", (long) config.workTime));
            preset.getElementsByTagName("breaktime").item(0).setTextContent(String.format("%s", (long) config.breakTime));
            preset.getElementsByTagName("longbreaktime").item(0).setTextContent(String.format("%s", (long) config.breakTimePomodoro));
            preset.getElementsByTagName("numCycles").item(0).setTextContent(Integer.toString(config.numCycles));
            preset.getElementsByTagName("numPomodoroCycles").item(0).setTextContent(Integer.toString(config.numPomodoroCycles));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(presetFilepath);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e)
        {
            throw new RuntimeException(e);
        }
    }

    public PomodoroConfig readTextFields()
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(timerConfigFilepath);

            PomodoroConfig config = new PomodoroConfig();

            // Retrieve the Variables
            config.workTime = Float.parseFloat(doc.getElementsByTagName("worktime").item(0).getTextContent().trim());
            config.breakTime = Float.parseFloat(doc.getElementsByTagName("breaktime").item(0).getTextContent().trim());
            config.breakTimePomodoro = Float.parseFloat(doc.getElementsByTagName("longbreaktime").item(0).getTextContent().trim());
            config.numCycles = Integer.parseInt(doc.getElementsByTagName("numCycles").item(0).getTextContent().trim());
            config.numPomodoroCycles = Integer.parseInt(doc.getElementsByTagName("numPomodoroCycles").item(0).getTextContent().trim());

            return config;
        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void writeTextFields(PomodoroConfig config)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(timerConfigFilepath);

            // Write the Variables
            doc.getElementsByTagName("worktime").item(0).setTextContent(String.format("%s", (long) config.workTime));
            doc.getElementsByTagName("breaktime").item(0).setTextContent(String.format("%s", (long) config.breakTime));
            doc.getElementsByTagName("longbreaktime").item(0).setTextContent(String.format("%s", (long) config.breakTimePomodoro));
            doc.getElementsByTagName("numCycles").item(0).setTextContent(Integer.toString(config.numCycles));
            doc.getElementsByTagName("numPomodoroCycles").item(0).setTextContent(Integer.toString(config.numPomodoroCycles));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(timerConfigFilepath);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e)
        {
            throw new RuntimeException(e);
        }
    }
}
