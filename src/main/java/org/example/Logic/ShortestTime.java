package org.example.Logic;

import org.example.Model.Server;
import org.example.Model.Task;
import org.example.View.Gui;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ShortestTime {

    public static int getShortestServer(Server servers[]) {
        int shortestServer = 0;
        int shortestTime = Integer.MAX_VALUE;
        for(int i = 0; i< servers.length; i++){
            int queueTime = 0;
            Server server = servers[i];
            Queue<Task> taskQueue = server.getTaskQueue();
            if(taskQueue.isEmpty()){
                shortestServer = i;
                break;
            }
            for(Task ent: taskQueue) {
                queueTime += ent.getServiceTime();
            }
            if (queueTime < shortestTime) {
                shortestTime = queueTime;
                shortestServer = i;
            }
        }
        return shortestServer;
    }

}
