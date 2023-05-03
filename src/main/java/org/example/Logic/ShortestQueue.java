package org.example.Logic;

import org.example.Model.Server;
import org.example.Model.Task;
import org.example.View.Gui;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ShortestQueue implements Runnable{
    private Server server;
    private Gui gui;

    public ShortestQueue(Server server, Gui gui) {
        this.server = server;
        this.gui = gui;
    }

    private int getShortestQueue(Vector<Queue<Task>> queues) {
        int shortestQueue = -1;
        for(int i = 0; i< queues.size(); i++){
            Queue<Task> queue = queues.get(i);
            if(queue.isEmpty()){
                shortestQueue = i;
                break;
            }
        }
        return shortestQueue;
    }

    @Override
    public void run() {
        int nrQueues = server.getNrQueues();
        Vector<Task> tasks = server.getTasks();
        int simulationTime = server.getSimulationTime();
        int totalWaitTime = 0;
        int totalServiceTime = 0;
        int totalTasks = 0;
        int tasksProcessed = 0;
        double avgServiceTime;
        double avgWaitTime;
        Vector<Queue<Task>> queues = new Vector<Queue<Task>>();
        for (int i = 0; i < nrQueues; i++) {
            queues.add(new LinkedList<Task>());
        }
        AtomicInteger currentTime = new AtomicInteger(0);
        try {
            while (tasksProcessed < tasks.size() || currentTime.get() <= simulationTime) {
                if(!tasks.isEmpty()) {
                    Task task = tasks.get(0);
                    if (task.getArrivalTime() <= currentTime.get()) {
                        int shortestQueue = getShortestQueue(queues);
                        if(shortestQueue != -1) {
                            queues.get(shortestQueue).add(task);
                            totalTasks++;
                            totalServiceTime += task.getServiceTime();
                            tasks.remove(0);
                        }
                        else totalWaitTime++;
                    }
                }
                gui.appendLogs("Time: "+ currentTime.get()+"\n");
                if(!tasks.isEmpty()) {
                    gui.appendLogs("Not in queue: ");
                    for (Task ent : tasks) {
                        gui.appendLogs("(" + ent.getId() + "," + ent.getArrivalTime() + "," + ent.getServiceTime() + ") ");
                    }
                }
                for (Queue<Task> queue : queues) {
                    if (!queue.isEmpty()) {
                        Task taskAux = queue.peek();
                        if (taskAux.getServiceTime() == 1) {
                            queue.poll();
                            tasksProcessed++;
                        } else {
                            int serviceTime = taskAux.getServiceTime() - 1;
                            queue.peek().setServiceTime(serviceTime);
                        }
                    }
                }
                gui.appendLogs("\n");
                int ind = 1;
                for(Queue<Task> queue: queues){
                    if(!queue.isEmpty()){
                        gui.appendLogs("Queue " + ind + ": ");
                        for(Task taskAux : queue)
                            gui.appendLogs("(" + taskAux.getId() + "," + taskAux.getArrivalTime() + "," + taskAux.getServiceTime() + "), ");
                        gui.appendLogs("\n");
                    }
                    else{
                        gui.appendLogs("Queue "+ ind +": Closed" + "\n");
                    }
                    ind++;
                }
                Thread.sleep(1000);
                currentTime.incrementAndGet();
            }
            avgServiceTime = (double) totalServiceTime / totalTasks;
            avgWaitTime = (double) totalWaitTime/totalTasks;
            gui.appendLogs("Average service time: " + avgServiceTime+"\n");
            gui.appendLogs("Average wait time: " + avgWaitTime+"\n");
            gui.appendLogs("\n\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

