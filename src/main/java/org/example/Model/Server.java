package org.example.Model;

import org.example.Model.Task;
import org.example.View.Gui;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements  Runnable{

    private BlockingQueue<Task> taskQueue;
    private int processedClients =0;
    private int waitTime;
    private Gui gui;

    public Server() {
        this.waitTime = 0;
        this.taskQueue = new LinkedBlockingDeque<>();
    }
    public BlockingQueue<Task> getTaskQueue() {
        return taskQueue;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getProcessedClients() {
        return processedClients;
    }

    public void setProcessedClients(int processedClients) {
        this.processedClients = processedClients;
    }

    public void addTask(Task task){
        this.taskQueue.add(task);
        this.waitTime = this.waitTime+task.getServiceTime();
    }

    @Override
    public void run() {
        if(this.taskQueue.size()!= 0){
            Task task = this.taskQueue.peek();
            if(task.getServiceTime()-1 > 0){
                task.setServiceTime(task.getServiceTime()-1);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            else{
                try{
                    this.taskQueue.take();
                    processedClients++;
                    }catch(InterruptedException e){
                    e.printStackTrace();
                    }
            }
        }
    }
}

