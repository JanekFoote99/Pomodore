package main.Pomodore;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.MonitorInfo;

public class XMLParser
{
    private final File configFilepath;

    public XMLParser(String path)
    {
        this.configFilepath = new File(path);
    }

    public PomodoroConfig readPreset(int presetId)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(configFilepath);

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
            Document doc = db.parse(configFilepath);

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
            StreamResult result = new StreamResult(configFilepath);
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

            Document doc = db.parse(configFilepath);

            PomodoroConfig config = new PomodoroConfig();

            Element timerVariables = (Element) doc.getElementsByTagName("timer").item(0);

            // Retrieve the Variables
            config.workTime = Float.parseFloat(timerVariables.getElementsByTagName("worktime").item(0).getTextContent().trim());
            config.breakTime = Float.parseFloat(timerVariables.getElementsByTagName("breaktime").item(0).getTextContent().trim());
            config.breakTimePomodoro = Float.parseFloat(timerVariables.getElementsByTagName("longbreaktime").item(0).getTextContent().trim());
            config.numCycles = Integer.parseInt(timerVariables.getElementsByTagName("numCycles").item(0).getTextContent().trim());
            config.numPomodoroCycles = Integer.parseInt(timerVariables.getElementsByTagName("numPomodoroCycles").item(0).getTextContent().trim());

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
            Document doc = db.parse(configFilepath);

            Element timerVariables = (Element) doc.getElementsByTagName("timer").item(0);

            // Write the Variables
            timerVariables.getElementsByTagName("worktime").item(0).setTextContent(String.format("%s", (long) config.workTime));
            timerVariables.getElementsByTagName("breaktime").item(0).setTextContent(String.format("%s", (long) config.breakTime));
            timerVariables.getElementsByTagName("longbreaktime").item(0).setTextContent(String.format("%s", (long) config.breakTimePomodoro));
            timerVariables.getElementsByTagName("numCycles").item(0).setTextContent(Integer.toString(config.numCycles));
            timerVariables.getElementsByTagName("numPomodoroCycles").item(0).setTextContent(Integer.toString(config.numPomodoroCycles));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(configFilepath);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void writeMonitorInformation(MonitorConfig config)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(configFilepath);

            Element windowConfig = (Element) doc.getElementsByTagName("window").item(0);

            windowConfig.getElementsByTagName("xPos").item(0).setTextContent(Integer.toString(config.x));
            windowConfig.getElementsByTagName("yPos").item(0).setTextContent(Integer.toString(config.y));
            windowConfig.getElementsByTagName("width").item(0).setTextContent(Integer.toString(config.width));
            windowConfig.getElementsByTagName("height").item(0).setTextContent(Integer.toString(config.height));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(configFilepath);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e)
        {
            throw new RuntimeException(e);
        }
    }

    public MonitorConfig readMonitorInformation()
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(configFilepath);

            Element windowVariables = (Element) doc.getElementsByTagName("window").item(0);

            int x = Integer.parseInt(windowVariables.getElementsByTagName("xPos").item(0).getTextContent().trim());
            int y = Integer.parseInt(windowVariables.getElementsByTagName("yPos").item(0).getTextContent().trim());
            int width = Integer.parseInt(windowVariables.getElementsByTagName("width").item(0).getTextContent().trim());
            int height = Integer.parseInt(windowVariables.getElementsByTagName("height").item(0).getTextContent().trim());

            return new MonitorConfig(x, y, width, height);
        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}