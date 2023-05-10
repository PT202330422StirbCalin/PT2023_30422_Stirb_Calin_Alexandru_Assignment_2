package org.example.Logic;

import org.example.Model.Server;
import org.example.Model.Task;
import org.example.View.Gui;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ShortestTime implements Runnable {
    private Server server;
    private Gui gui;
    private int threadIndex;

    public ShortestTime(Server server, Gui gui, int threadIndex) {
        this.server = server;
        this.gui = gui;
        this.threadIndex = threadIndex;
    }

    public ShortestTime(Server server, Gui gui) {
        this.server = server;
        this.gui = gui;
    }

    private int getQueueIndex(Vector<Queue<Task>> queues) {
        int shortestQueue = 0;
        int shortestTime = Integer.MAX_VALUE;
        for(int i = 0; i< queues.size(); i++){
            int queueTime = 0;
            Queue<Task> queue = queues.get(i);
            if(queue.isEmpty()){
                shortestQueue = i;
                break;
            }
            for(Task ent: queue) {
                queueTime += ent.getServiceTime();
            }
            if (queueTime < shortestTime) {
                shortestTime = queueTime;
                shortestQueue = i;
            }
        }
        return shortestQueue;
    }

        @Override
        public void run () {
        int nrQueues = server.getNrQueues();
        Vector<Task> tasks = server.getTasks();
        int simulationTime = server.getSimulationTime();
        int totalWaitTime = 0;
        int totalServiceTime = 0;
        int totalTasks = 0;
        int tasksProcessed = 0;
        double avgServiceTime;
        double avgWaitTime;
        int size = tasks.size();
        int index =0;
        Vector<Queue<Task>> queues = new Vector<Queue<Task>>();
        for (int i = 0; i < nrQueues; i++) {
            queues.add(new LinkedList<Task>());
        }
        AtomicInteger currentTime = new AtomicInteger(0);
        try {
            while (tasksProcessed < size && currentTime.get() <= simulationTime) {
                if (!tasks.isEmpty() && index < tasks.size()) {
                    for(int i = index; i<tasks.size();i++) {
                        //if(i > index + nrQueues)
                            //break;
                        Task task = tasks.get(i);
                        if (task.getArrivalTime() <= currentTime.get()) {
                            int queueIndex = getQueueIndex(queues);
                            queues.get(queueIndex).add(task);
                            totalTasks++;
                            totalServiceTime += task.getServiceTime();
                            //tasks.remove(0);
                            index = i+1;
                        }
                    }
                }
                gui.appendLogs("Time: " + currentTime.get() + "\n");
                if (!tasks.isEmpty() && index < tasks.size()) {
                    gui.appendLogs("Not in queue: ");
                    for (int i = index; i<tasks.size();i++) {
                        Task ent = tasks.get(i);
                        gui.appendLogs("(" + ent.getId() + "," + ent.getArrivalTime() + "," + ent.getServiceTime() + ") ");
                    }
                    gui.appendLogs("\n");
                }
                for (Queue<Task> queue : queues) {
                    if (!queue.isEmpty()) {
                        if (queue.size() > 1) {
                            totalWaitTime++;
                        }
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

                int ind = 1;
                for (Queue<Task> queue : queues) {
                    if (!queue.isEmpty()) {
                        gui.appendLogs("Queue " + ind + ": ");
                        for (Task taskAux : queue)
                            gui.appendLogs("(" + taskAux.getId() + "," + taskAux.getArrivalTime() + "," + taskAux.getServiceTime() + "), ");
                        gui.appendLogs("\n");
                    } else {
                        gui.appendLogs("Queue " + ind + ": Closed" + "\n");
                    }
                    ind++;
                }
                gui.appendLogs("\n");
                Thread.sleep(1000);
                currentTime.incrementAndGet();
            }
            avgServiceTime = (double) totalServiceTime / totalTasks;
            avgWaitTime = (double) totalWaitTime / totalTasks;
            gui.appendLogs("\nEnd of simulation logs:\n");
            gui.appendLogs("Average service time: " + avgServiceTime + "\n");
            gui.appendLogs("Average wait time: " + avgWaitTime + "\n");
            gui.appendLogs("Nr of processed tasks: "+ tasksProcessed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
