package core.threads.basic;

/**
 * CounterRunnableDemo shows how to start and run a counter in a separate thread by using the Runnable interface
 */
public class CounterRunnableDemo implements Runnable {
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(i);

            // simulate a long lasting operation by waiting some time
            try {
                Thread.sleep(i * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t2 = new Thread(new CounterRunnableDemo());
        t2.start();
    }
}
