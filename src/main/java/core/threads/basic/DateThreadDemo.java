package core.threads.basic;

import java.time.LocalDateTime;

/**
 * DateThreadDemo shows how to start and run a timestamp-generator in a separate thread by extending the Thread class
 */
public class DateThreadDemo extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(LocalDateTime.now());
        }
    }

    public static void main(String[] args) {
        Thread t = new DateThreadDemo();
        t.start();
    }
}

