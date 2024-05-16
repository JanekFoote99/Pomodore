package main.Pomodore;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TODOList extends JPanel
{
    private DefaultListModel<String> listModel;
    private JList<String> todoJList;

    public TODOList()
    {
        setPreferredSize(new Dimension(200, 600));
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        todoJList = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(todoJList);
        add(scrollPane, BorderLayout.CENTER);

        addItem("Task 1");
        addItem("Task 2");
        addItem("Task 3");
    }

    public void addItem(String text)
    {
        listModel.addElement(text);
    }
}
