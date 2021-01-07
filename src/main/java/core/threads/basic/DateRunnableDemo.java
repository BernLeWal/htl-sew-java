package core.threads.basic;

import java.time.LocalDateTime;

/**
 * DateRunnableDemo shows how to start and run a timestamp-generator in a separate thread by using the Runnable interface
 */
public class DateRunnableDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            // generate a timestamp and print int
            System.out.println(LocalDateTime.now());
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread( new DateRunnableDemo() );
        t1.start();
    }
}

