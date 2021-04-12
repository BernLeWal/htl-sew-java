package core.threads.webcrawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolWebCrawlerDemo shows how runnables are executed in parallel by
 * using a thread-pool of 10 threads controlled by an ExecutorService.
 */
public class ThreadPoolWebCrawlerDemo {
    public static final int NUM_THREADS = 10;

    public static void main(String[] args) throws InterruptedException {
        var sites = new LockedSites();
        sites.add("https://www.orf.at");

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(new WebCrawler( sites ));
        }

        var startTime = System.currentTimeMillis();
        System.out.printf("Starting with %d threads...\n", NUM_THREADS);
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();
        // Wait until all threads are finish with a 10 seconds timeout
        executor.awaitTermination(10, TimeUnit.SECONDS);
        // Stop all still running threads.
        executor.shutdownNow();


        long duration = (System.currentTimeMillis()-startTime);
        System.out.printf("Thread stopped after %d ms. %d crawled (%f/sec)\n", duration, sites.getNrCrawledSites(), (float)sites.getNrCrawledSites()*1000.0f/duration);
    }
}
