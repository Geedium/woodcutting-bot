package com.me.woodcutter;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class GUI {
    public static void run(Woodcutter main) {
        JFrame jFrame = new JFrame("Woodcutting Bot");
        jFrame.setSize(300, 500);
        jFrame.setResizable(false);

        JPanel settingsPanel = new JPanel();
        TitledBorder leftBorder = BorderFactory.createTitledBorder("Settings");
        leftBorder.setTitleJustification(TitledBorder.LEFT);
        settingsPanel.setBorder(leftBorder);
        settingsPanel.setLayout(null);
        settingsPanel.setBounds(5, 200, 280, 180);
        jFrame.add(settingsPanel);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(null);
        startPanel.setBounds(5, 350, 70, 20);
        jFrame.add(startPanel);

        JLabel treeSelection = new JLabel("Select a Tree:");
        treeSelection.setBounds(10, 40, 95, 20);
        settingsPanel.add(treeSelection);

        JComboBox<String> treeList = new JComboBox<String>(new String[] { "None", "Tree", "Oak", "Willow", "Yew", "Magic tree"});
        treeList.addActionListener(e -> main.tree = (String) treeList.getSelectedItem());
        treeList.setBounds(160, 40, 110, 20);
        settingsPanel.add(treeList);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            jFrame.setVisible(false);
        });

        startButton.setBounds(5, 390, 70, 20);
        startPanel.add(startButton);

        jFrame.setVisible(true);
    }
}
