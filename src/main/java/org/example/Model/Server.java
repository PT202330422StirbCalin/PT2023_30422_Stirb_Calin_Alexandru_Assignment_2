package org.example.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Server implements  Runnable{

    private BlockingQueue<Task> taskQueue;
    private int processedTasks =0;
    private int waitTime;

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

    public int getProcessedTasks() {
        return processedTasks;
    }

    public void setProcessedTasks(int processedTasks) {
        this.processedTasks = processedTasks;
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
                    processedTasks++;
                    }catch(InterruptedException e){
                    e.printStackTrace();
                    }
            }
        }
    }
}

