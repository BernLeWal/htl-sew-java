package core.threads.sync;

public class SynchronizedCounter implements CounterDemoInterface {
    private int number = 0;

    public void run() {
        System.out.println("Running SynchronizedCounter");
        Thread th1 = new CounterThread(this, 1, 100);
        Thread th2 = new CounterThread(this, 2, 100);
        th1.start();
        th2.start();

        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Final result is " + number);
    }

    public synchronized void increase() {
        int oldValue = number;
        try {
            Thread.sleep((int) (Math.random() * 100.0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        number = oldValue + 1;
        System.out.println(" from " + oldValue + " to " + number);
    }
}
