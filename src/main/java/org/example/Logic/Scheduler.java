package org.example.Logic;

import org.example.Control.SimulationManager;
import org.example.Model.Server;
import org.example.Model.Task;
import org.example.Strategy;
import org.example.View.Gui;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Scheduler {

    public static void simStartup(Gui gui, int nrQueues, Vector<Task> tasks, int simulationTime, Strategy strategy) {

        Server servers[] = new Server[nrQueues];
        for (int i = 0; i < nrQueues; i++) {
            servers[i]= new Server();
        }
        int assigned[] = new int[tasks.size()];
        for(int i=0;i<tasks.size();i++){
            assigned[i]=0;
        }
        simulation(servers,assigned,gui,nrQueues,tasks,simulationTime,strategy);
    }

    public static void simulation(Server servers[],int assigned[],Gui gui, int nrQueues, Vector<Task> tasks, int simulationTime, Strategy strategy) {
            final int[] totalWaitTime = {0};
            final int[] totalServiceTime = {0};
            final AtomicInteger[] tasksProcessed = {new AtomicInteger()};
            final AtomicInteger[] clientsInOneCycle = {new AtomicInteger()};
            AtomicInteger peakHour = new AtomicInteger(-1);
            final AtomicReference<Double>[] avgServiceTime = new AtomicReference[]{new AtomicReference<>((double) 0)};
            final AtomicReference<Double>[] avgWaitTime = new AtomicReference[]{new AtomicReference<>((double) 0)};
            AtomicInteger currentTime = new AtomicInteger(0);
            // without this thread it won't update in real time because of synchronization
            Thread mainThread = new Thread(() -> {
                while (tasksProcessed[0].get() < tasks.size() && currentTime.get() <= simulationTime) {
                    int clientsInQueues = 0;
                    int queueIndex;
                    int i = 0;
                    for (Task task : tasks) {
                        if (task.getArrivalTime() == currentTime.get()) {
                            assigned[i] = 1;
                            if (strategy == Strategy.SHORTEST_QUEUE) {
                                queueIndex = ShortestQueue.getShortestServer(servers);
                            } else {
                                queueIndex = ShortestTime.getShortestServer(servers);
                            }
                            servers[queueIndex].addTask(task);
                            totalWaitTime[0] = totalWaitTime[0] + servers[queueIndex].getWaitTime();
                            totalServiceTime[0] += task.getServiceTime();

                        }
                        i++;
                    }
                    gui.appendLogs("Time: " + currentTime.get() + "\n");
                    gui.appendLogs("Waiting clients:");
                    for (int j = 0; j < tasks.size(); j++) {
                        Task ent = tasks.get(j);
                        if (assigned[j] == 0) {
                            gui.appendLogs("(" + ent.getId() + "," + ent.getArrivalTime() + "," + ent.getServiceTime() + ") ");
                        }
                    }
                    gui.appendLogs("\n");
                    int ind = 1;
                    for (Server server : servers) {
                        if (server.getTaskQueue().isEmpty()) {
                            gui.appendLogs("Queue " + ind + ": Closed" + "\n");
                        } else {
                            gui.appendLogs("Queue " + ind + ": ");
                            for (Task taskAux : server.getTaskQueue())
                                gui.appendLogs("(" + taskAux.getId() + "," + taskAux.getArrivalTime() + "," + taskAux.getServiceTime() + "), ");
                            gui.appendLogs("\n");
                        }
                        ind++;
                    }
                    for (int j = 0; j < nrQueues; j++) {
                        servers[j].run();
                        clientsInQueues += servers[j].getTaskQueue().size();
                    }
                    if (clientsInQueues > clientsInOneCycle[0].get()) {
                        clientsInOneCycle[0].set(clientsInQueues);
                        peakHour.set(currentTime.get());
                    }
                    currentTime.incrementAndGet();
                }
                for (Server server : servers) {
                    tasksProcessed[0].addAndGet(server.getProcessedClients());
                }
                avgServiceTime[0].set((double) totalServiceTime[0] / tasksProcessed[0].get());
                avgWaitTime[0].set((double) totalWaitTime[0] / tasksProcessed[0].get());
                gui.appendLogs("\nEnd of simulation logs:\n");
                gui.appendLogs("Average service time: " + avgServiceTime[0] + "\n");
                gui.appendLogs("Average wait time: " + avgWaitTime[0] + "\n");
                gui.appendLogs("Nr of processed tasks: " + tasksProcessed[0] + "\n");
                gui.appendLogs("Peak hour: " + peakHour.get());
                SimulationManager.writeLogToFile(gui.logs);
            });
            mainThread.start();

    }
}
