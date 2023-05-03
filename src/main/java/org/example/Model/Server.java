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

}
