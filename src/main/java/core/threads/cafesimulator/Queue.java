package core.threads.cafesimulator;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private final List<Person> list = new ArrayList<>();
    private final int numProducers;

    private int producersCompleted;

    public Queue(int numProducers) {
        this.numProducers = numProducers;
    }

    public int getCount() {
        synchronized (list) {
            return list.size();
        }
    }

    public boolean isCompleted() {
        synchronized (list) {
            return (list.size()==0) && (producersCompleted==numProducers);
        }
    }

    public void producerCompleted() {
        producersCompleted++;
    }

    public void enqueue(Person person) {
        synchronized (list)
        {
            list.add(person);
        }
    }

    public Person tryDequeue() {
        synchronized (list)
        {
            if (list.size()>0) {
                return list.remove(0);
            }
            else {
                return null;
            }
        }
    }
}
