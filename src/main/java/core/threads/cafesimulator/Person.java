package core.threads.cafesimulator;

import java.util.Random;

public class Person {
    private final String name;
    private final long startWaitTimestamp;
    private final long waitingMillis;
    private final long processingMillis;

    public Person(String name) {
        this.name = name;
        this.startWaitTimestamp = System.currentTimeMillis();   // Zeitstempel wo das Warten begann
        this.waitingMillis = 100 + new Random().nextInt(400);    // wie lang kann man warten bis es zu spät ist
        this.processingMillis = 10 + new Random().nextInt(400);  // wie lange das Geschäft dauert
        System.out.println("Person " + name + ": geht zur Kantine.");
    }

    public void process() {
        try {
            Thread.sleep(processingMillis);
        } catch (InterruptedException e) {
            // IGNORED
        }

        if( (System.currentTimeMillis()-startWaitTimestamp) < waitingMillis )
            System.out.println(name + ": Njam, njam, njam ... ist das gut!");
        else
            System.out.println(name + ": OOOh nein, ich bin verhungert....");
    }
}
