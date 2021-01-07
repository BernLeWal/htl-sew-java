package core.threads.sync;

public class CounterThread extends Thread {
    private CounterDemoInterface counter;
    private int threadNr;
    private int repeat;

    public CounterThread(CounterDemoInterface counter, int threadNr, int repeat)
    {
        this.counter = counter;
        this.threadNr = threadNr;
        this.repeat = repeat;
        System.out.println("Thread " + threadNr + " counts " + repeat + " times up.");
    }

    @Override
    public void run()
    {
        for(int i=0; i<repeat; i++) {
            System.out.print("Thread " + threadNr + "(step " + i + ") counts ");
            counter.increase();
        }
    }
}
