package patterns.producerconsumer;

import java.util.Random;

public class PeopleProducer extends Thread {
    private final String name;
    private final Queue queue;
    private final String[] personNames;

    public PeopleProducer(String name, Queue queue, String[] personNames) {
        this.name = name;
        this.queue = queue;
        this.personNames = personNames;
    }

    @Override
    public void run() {
        System.out.println("PeopleProducer " + name + " gestartet...");
        long waitMillis = 100 + new Random().nextInt(400);
        for (String personName : personNames) {
            try {
                Thread.sleep(waitMillis);
            } catch (InterruptedException e) {
                // IGNORED
            }
            queue.enqueue(new Person(name + "-" + personName));
        }
        System.out.println("PeopleProducer " + name + " beendet.");
        queue.producerCompleted();
    }
}
