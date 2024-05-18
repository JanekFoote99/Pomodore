package main.Pomodore.TODOList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TODOList
{
    private DefaultListModel<TODOItem> listModel;
    private JList<TODOItem> todoJList;

    public TODOList(JPanel panel)
    {
        this.listModel = new DefaultListModel<>();
        this.todoJList = new JList<>(this.listModel);
        this.todoJList.setCellRenderer(new TODORenderer());

        this.todoJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = todoJList.locationToIndex(e.getPoint());
                if (index != -1) {
                    TODOItem item = listModel.getElementAt(index);
                    item.setStatus(!item.getStatus());
                    todoJList.repaint(todoJList.getCellBounds(index, index));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(todoJList);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        addItem("Task 1");
        addItem("Task 2");
        addItem("Task 3");
    }

    public void addItem(String text)
    {
        listModel.addElement(new TODOItem(text));
    }
}
