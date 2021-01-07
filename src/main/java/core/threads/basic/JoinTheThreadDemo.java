package core.threads.basic;

/**
 * JoinTheThreadDemo demonstrates why the .join() method is necessary to synchronize the (timline of the) operation of multiple threads.
 * <p>
 * The main()-method runs within the main-thread which generates an extra processing-thread with the JoinerThread class.
 */
public class JoinTheThreadDemo {
    /**
     * JoinerThread is a inner class for the operation in an extra thread.
     */
    static class JoinerThread extends Thread {
        public String result;

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // IGNORED
            }
            result = "finished";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        notJoined();
        System.out.println();
        joined();

//        JoinerThread t2 = new JoinerThread();
//        t2.start();
//        t2.join();                      // wait for t2.run() to finish
//        System.out.println(t2.result);  // returns "finished"
    }

    public static void notJoined() {
        System.out.println("Start Thread...");
        JoinerThread t1 = new JoinerThread();
        t1.start();
        System.out.println(t1.result);   // returns null - because t1.run() not finished yet
    }

    public static void joined() throws InterruptedException {
        System.out.println("Start Thread...");
        JoinerThread t1 = new JoinerThread();
        t1.start();
        System.out.println("Waiting for the results...");
        t1.join();
        System.out.println(t1.result);   // return "finished" - because t1.run() was finished before
    }
}

