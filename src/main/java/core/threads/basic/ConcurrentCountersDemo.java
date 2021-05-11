package core.threads.basic;

public class ConcurrentCountersDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread( () -> run() );
        t1.start();
        Thread t2 = new Thread( () -> run() );
        t2.start();
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.join();
        t2.join();
    }

    private static void run() {
        for (int i=0; i<20;i++) {
            try {
                Thread.sleep(100);
                System.out.println( Thread.currentThread().getName() );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
