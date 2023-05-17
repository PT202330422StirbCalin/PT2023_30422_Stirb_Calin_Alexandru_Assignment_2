package org.example.Logic;

import org.example.Model.Server;
import org.example.Model.Task;
import org.example.View.Gui;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ShortestQueue {
    private Server server;
    private Gui gui;
    private int threadIndex;

    public ShortestQueue(Server server, Gui gui) {
        this.server = server;
        this.gui = gui;
    }

    public ShortestQueue(Server server, Gui gui, int threadIndex) {
        this.server = server;
        this.gui = gui;
        this.threadIndex = threadIndex;
    }

    public static int getShortestServer(Server servers[]) {
        int shortestServer = -1;
        int shortestQueueSize = Integer.MAX_VALUE;

        for (int i = 0; i < servers.length; i++) {
            Server server = servers[i];
            Queue<Task> taskQueue = server.getTaskQueue();

            if (taskQueue.isEmpty()) {
                shortestServer = i;
                break;
            }

            if (taskQueue.size() < shortestQueueSize) {
                shortestServer = i;
                shortestQueueSize = taskQueue.size();
            }
        }

        return shortestServer;
    }
}
