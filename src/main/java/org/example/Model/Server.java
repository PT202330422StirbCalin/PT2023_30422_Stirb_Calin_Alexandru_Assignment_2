package org.example.Model;

import org.example.Model.Task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server{
    private Vector<Task> tasks;
    private Vector<Queue<Task>> queues;
    private int nrQueues,simulationTime;

    public Server(Vector<Task> tasks, int nrQueues, int simulationTime) {
        this.tasks = tasks;
        this.nrQueues = nrQueues;
        this.simulationTime = simulationTime;
    }

    public Server() {
    }

    public int getNrQueues() {
        return nrQueues;
    }

    public void setNrQueues(int nrQueues) {
        this.nrQueues = nrQueues;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(int simulationTime) {
        this.simulationTime = simulationTime;
    }

    public Vector<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Vector<Task> tasks) {
        this.tasks = tasks;
    }

    /*
    public void shortestTime(Vector<Task> tasks,int nrQueues, int simulationTime ){
        int totalWaitTime = 0;
        int totalServiceTime = 0;
        int totalTasks = 0;
        int tasksProcessed = 0;
        int index = 0;
        double avgServiceTime;
        Vector<Queue<Task>> queues = new Vector<Queue<Task>>();
        for(int i =0 ; i < nrQueues; i++){
            queues.add(new LinkedList<Task>());
        }
        Vector<Thread> threads = new Vector<Thread>();
        for(int i = 0; i< nrQueues;i++){
            threads.add(new Thread(new Server(tasks,nrQueues,simulationTime)));
        }
        for(Thread thread:threads){
            thread.start();
        }
        AtomicInteger currentTime = new AtomicInteger(0);
        try{
            while(tasksProcessed < tasks.size() && currentTime.get() <= simulationTime){
                Task task = tasks.get(index);
                if(task.getArrivalTime() >= currentTime.get()){
                    int shortestQueue = getShortestQueue(queues);
                    queues.get(shortestQueue).add(task);
                    index++;
                    totalTasks++;
                    totalServiceTime+= task.getServiceTime();
                    System.out.println("Queue "+ shortestQueue + "(" + task.getId()+","+task.getArrivalTime()+","+task.getServiceTime()+"\n");
                    tasks.remove(task);
                }
                for(Queue<Task> queue: queues){
                    if(!queue.isEmpty()){
                        Task taskAux = queue.peek();
                        if(taskAux.getServiceTime() == 0){
                            queue.poll();
                            tasksProcessed++;
                        }
                        else {
                            int serviceTime = taskAux.getServiceTime() -1;
                            taskAux.setServiceTime(serviceTime);
                        }
                    }
                }
                Thread.sleep(1000);
                currentTime.incrementAndGet();
            }
            for(Thread thread:threads){
                thread.join();
            }
            avgServiceTime = (double)totalServiceTime/totalTasks;
            System.out.println("Average service time: "+ avgServiceTime);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
*/
}
