package org.example.Model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

public class Task {
    private int id;
    private int arrivalTime;
    private int serviceTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public static Vector<Task> generateRandomClients(int nrClients, int maxArrivalTime, int minArrivalTime, int minServiceTime, int maxServiceTime){
        Vector<Task> tasks = new Vector<>();
        for(int i=1;i<=nrClients;i++){
            Random rn = new Random();
            int serviceTime = minServiceTime + rn.nextInt(maxServiceTime-1);
            int arrivalTime = minArrivalTime + rn.nextInt(maxArrivalTime-1);
            Task task = new Task(i,arrivalTime,serviceTime);
            tasks.add(task);
        }
        return tasks;
    }

    public static Vector<Task>createSortedClientsByArrival(Vector<Task> tasks){
        Vector<Task> result = new Vector<>();
        for(Task ent: tasks){
            result.add(ent);
        }
        Collections.sort(result, Comparator.comparing(Task::getArrivalTime));
        return result;
    }

}
