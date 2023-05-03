package org.example.Control;

import org.example.Logic.ShortestQueue;
import org.example.Logic.ShortestTime;
import org.example.Model.Server;
import org.example.Model.Task;
import org.example.View.Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class SimulationManager implements ActionListener {
    private  Gui gui;
    private Server server;
    private ShortestTime shortestTime;
    private ShortestQueue shortestQueue;
    public SimulationManager(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gui.startButton){
            gui.clearLogs();
            int nrClients = Integer.parseInt(gui.t1.getText());
            int nrQueues = Integer.parseInt(gui.t2.getText());
            int simulationTime = Integer.parseInt(gui.t3.getText());
            int minArrivalTime = Integer.parseInt(gui.t4.getText());
            int maxArrivalTime = Integer.parseInt(gui.t5.getText());
            int minServiceTime = Integer.parseInt(gui.t6.getText());
            int maxServiceTime = Integer.parseInt(gui.t7.getText());
            Vector<Task> tasks = Task.generateRandomClients(nrClients,maxArrivalTime,minArrivalTime,minServiceTime,maxServiceTime);
            Vector<Task> sorted = Task.createSortedClientsByArrival(tasks);
            server = new Server(sorted,nrQueues,simulationTime);
            shortestTime = new ShortestTime(server,gui);
            shortestQueue = new ShortestQueue(server,gui);
            if(gui.options.getSelectedItem() == "Shortest queue strategy"){
                shortestQueue.run();
            }
            else if (gui.options.getSelectedItem() == "Shortest time strategy") {
                shortestTime.run();
            }
            writeLogToFile(gui.logs);
        }
    }
    public void writeLogToFile(JTextArea logs){
        try(PrintWriter writer = new PrintWriter("log.txt")){
            String logText = logs.getText();
            writer.write(logText);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
