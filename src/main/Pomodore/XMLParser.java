package main.Pomodore;

import main.Pomodore.TODOList.TODOItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.MonitorInfo;
import java.util.ArrayList;
import java.util.List;

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
        } catch (IOException e)
        {
            System.out.println("Error while loading Configuration File. Loading standard Preset!");
            return new PomodoroConfig(4, 25, 5, 1, 20);
        } catch (ParserConfigurationException e)
        {
            System.out.println("Error while configuring the XML-Parser. Loading standard Preset!");
            return new PomodoroConfig(4, 25, 5, 1, 20);
        } catch (SAXException e)
        {
            System.out.println("SAX Parser Error. Loading standard Preset!");
            return new PomodoroConfig(4, 25, 5, 1, 20);
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

            transform(doc);
        } catch (ParserConfigurationException e)
        {
            System.out.println("Error while configuring the XML-Parser. Couldn't write Preset!");
        } catch (SAXException e)
        {
            System.out.println("SAX Parser Error. Couldn't write Preset!");
        } catch (IOException e)
        {
            System.out.println("Error while loading Configuration File. Couldn't write Preset!");
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
        } catch (IOException e)
        {
            System.out.println("Error while loading Configuration File. Loading standard Timer Variables!");
            return new PomodoroConfig(4, 25, 5, 1, 20);
        } catch (ParserConfigurationException e)
        {
            System.out.println("Error while configuring the XML-Parser. Loading standard Timer Variables!");
            return new PomodoroConfig(4, 25, 5, 1, 20);
        } catch (SAXException e)
        {
            System.out.println("SAX Parser Error. Loading standard Timer Variables!");
            return new PomodoroConfig(4, 25, 5, 1, 20);
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

            transform(doc);
        } catch (ParserConfigurationException e)
        {
            System.out.println("Error while configuring the XML-Parser. Couldn't write Timer Variables!");
        } catch (SAXException e)
        {
            System.out.println("SAX Parser Error. Couldn't write Timer Variables!");
        } catch (IOException e)
        {
            System.out.println("Error while loading Configuration File. Couldn't write Timer Variables!");
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
        } catch (IOException e)
        {
            System.out.println("Error while loading Configuration File. Loading standard Window Position!");
            return new MonitorConfig(0, 0, 1600, 1200);
        } catch (ParserConfigurationException e)
        {
            System.out.println("Error while configuring the XML-Parser. Loading standard Window Position!");
            return new MonitorConfig(0, 0, 1600, 1200);
        } catch (SAXException e)
        {
            System.out.println("SAX Parser Error. Loading standard Window Position!");
            return new MonitorConfig(0, 0, 1600, 1200);
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

            transform(doc);
        } catch (ParserConfigurationException e)
        {
            System.out.println("Error while configuring the XML-Parser. Couldn't write Monitor Information!");
        } catch (SAXException e)
        {
            System.out.println("SAX Parser Error. Couldn't write Monitor Information!");
        } catch (IOException e)
        {
            System.out.println("Error while loading Configuration File. Couldn't write Monitor Information!");
        }
    }

    // Writes all the TODOItems that are not finished/ticked
    public ArrayList<TODOItem> readTodos()
    {
        try
        {
            ArrayList<TODOItem> items = new ArrayList<>();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(configFilepath);

            Element todos = (Element) doc.getElementsByTagName("todos").item(0);

            NodeList nl = todos.getElementsByTagName("todoItem");

            for (int i = 0; i < nl.getLength(); i++)
            {
                Element item = (Element) nl.item(i);

                String todoText = item.getTextContent().trim();
                TODOItem curItem = new TODOItem(todoText);

                items.add(curItem);
            }

            return items;
        } catch (IOException e)
        {
            System.out.println("Error while loading Configuration File. Window position couldn't be retrieved");
            return new ArrayList<TODOItem>();
        } catch (ParserConfigurationException e)
        {
            System.out.println("Error while configuring the XML-Parser");
            return new ArrayList<TODOItem>();
        } catch (SAXException e)
        {
            System.out.println("SAX Parser Error");
            return new ArrayList<TODOItem>();
        }
    }

    public void writeTodos(ArrayList<TODOItem> todoList)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(configFilepath);

            Element todos = (Element) doc.getElementsByTagName("todos").item(0);

            // wipe last saved data
            while (todos.hasChildNodes())
            {
                todos.removeChild(todos.getFirstChild());
            }

            for (TODOItem todoItem : todoList)
            {
                if (!todoItem.getCheckBox().isSelected())
                {
                    Element todo = doc.createElement("todoItem");
                    todo.setTextContent(todoItem.getTextField().getText());
                    todos.appendChild(todo);
                }
            }

            // TODO: Setup Formatting for todos section
            transform(doc);
        } catch (ParserConfigurationException e)
        {
            System.out.println("Error while configuring the XML-Parser. Couldn't write Todos!");
        } catch (SAXException e)
        {
            System.out.println("SAX Parser Error. Couldn't write Todos!");
        } catch (IOException e)
        {
            System.out.println("Error while loading Configuration File. Couldn't write Todos!");
        }
    }

    void transform(Document doc)
    {
        try
        {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            // Creates Whitespaces between XML Nodes
            /*transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");*/

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(configFilepath);
            transformer.transform(source, result);
        } catch (TransformerException e)
        {
            throw new RuntimeException(e);
        }
    }
}
