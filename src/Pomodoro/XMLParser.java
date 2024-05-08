package Pomodoro;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser
{
    private final File filepath;

    public XMLParser(String path)
    {
        this.filepath = new File(path);
    }

    public PomodoroConfig readPreset(int presetId)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(filepath);

            PomodoroConfig config = new PomodoroConfig();

            // preset contains all the information of the selected Preset Button
            Element preset = (Element)doc.getElementsByTagName("preset").item(presetId);

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

    public void writePreset(PomodoroConfig config)
    {

    }
}
