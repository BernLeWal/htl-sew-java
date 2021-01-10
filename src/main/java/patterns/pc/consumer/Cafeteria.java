package patterns.pc.consumer;

import patterns.pc.jobs.Person;
import patterns.pc.queues.Queue;

public class Cafeteria extends Thread {
    private final String name;
    private final Queue queue;

    public Cafeteria(String name, Queue queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Cafeteria " + name + " gestartet...");
        while (!queue.isCompleted()) {
            Person person = queue.tryDequeue();
            if (person!=null) {
                person.process();
            }
        }
        System.out.println("Cafeteria " + name + " beendet.");
    }
}
