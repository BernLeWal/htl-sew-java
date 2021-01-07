package core.threads.sync;

/**
 * CounterDemo demonstrates the necessitiy of synchronizing threads which share the same resources.
 */
public class CounterDemo {
    public static void main(String[] args) {
        new UnsynchronizedCounter().run();
        System.out.println();
        new SynchronizedCounter().run();
    }
}
