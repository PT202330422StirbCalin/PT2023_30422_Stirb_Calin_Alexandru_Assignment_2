package org.example.View;


import org.example.Control.SimulationManager;

import javax.swing.*;
import java.awt.*;

public class Gui {
    public JTextField t1, t2,t3,t4,t5,t6,t7;
    public JComboBox options;
    private static JPanel panel;
    public JLabel p1, p2,p3,p4,p5,p6,p7;
    private static JFrame frame;
    public JButton startButton;
    public JTextArea logs;
    public JScrollPane scroll;

    public Gui() {
        ImageIcon background = new ImageIcon("C:\\Users\\stirb\\Downloads\\Facultate\\mvc\\background.jpg");
        Image img = background.getImage();
        Image temp = img.getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);
        JLabel back = new JLabel(background);
        back.setLayout(null);
        back.setBounds(0, 0, 1000, 800);

        String[] op = {"Shortest queue strategy","Shortest time strategy"};
        frame = new JFrame();
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Thread Queue simulation");

        panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        p1 = new JLabel("Number of clients:");
        p1.setBounds(10, 20, 200, 25);
        panel.add(p1);
        t1 = new JTextField(165);
        t1.setBounds(225, 20, 75, 25);
        panel.add(t1);

        p2 = new JLabel("Number of queues:");
        p2.setBounds(10, 80, 200, 25);
        panel.add(p2);
        t2 = new JTextField(165);
        t2.setBounds(225, 80, 75, 25);
        panel.add(t2);

        p3 = new JLabel("Simulation Time:");
        p3.setBounds(10, 140, 200, 25);
        panel.add(p3);
        t3 = new JTextField(165);
        t3.setBounds(225, 140, 75, 25);
        panel.add(t3);

        p4 = new JLabel("Min value of arrivalTime:");
        p4.setBounds(10, 200, 200, 25);
        panel.add(p4);
        t4 = new JTextField(165);
        t4.setBounds(225, 200, 75, 25);
        panel.add(t4);

        p5 = new JLabel("Max value of arrivalTime:");
        p5.setBounds(10, 260, 200, 25);
        panel.add(p5);
        t5 = new JTextField(165);
        t5.setBounds(225, 260, 75, 25);
        panel.add(t5);

        p6 = new JLabel("Min value of serviceTime:");
        p6.setBounds(10, 320, 200, 25);
        panel.add(p6);
        t6 = new JTextField(165);
        t6.setBounds(225, 320, 75, 25);
        panel.add(t6);

        p7 = new JLabel("Max value of serviceTime:");
        p7.setBounds(10, 380, 200, 25);
        panel.add(p7);
        t7 = new JTextField(165);
        t7.setBounds(225, 380, 75, 25);
        panel.add(t7);

        options = new JComboBox<>(op);
        options.setBounds(10, 440, 250, 25);
        panel.add(options);

        startButton = new JButton("Start simulation");
        startButton.setBounds(10, 500, 125, 25);
        SimulationManager controller = new SimulationManager(this);
        startButton.addActionListener(controller);
        panel.add(startButton);


        logs = new JTextArea(1000,1000);
        logs.setBounds(350,20,600,700);
        logs.setEditable(false);
        scroll = new JScrollPane(logs);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(350,20,600,700);
        panel.add(scroll);
        panel.add(back);
        frame.setVisible(true);
    }

    public void appendLogs(String message){
        logs.append(message);
    }
    public void clearLogs(){
        logs.setText("");
    }
}
