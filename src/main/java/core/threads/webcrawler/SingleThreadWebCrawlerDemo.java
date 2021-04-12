package core.threads.webcrawler;

/**
 * SingleThreadWebCrawlerDemo shows how runnables are executed in an extra thread
 * and how these "neverending" threads are interrupted from the main-thread.
 */
public class SingleThreadWebCrawlerDemo {
    public static void main(String[] args) throws InterruptedException {
        var sites = new SynchronizedSites();
        sites.add("https://www.orf.at");

        WebCrawler worker = new WebCrawler( sites );
        Thread executor = new Thread( worker );
        //executor.setName("T1");
        executor.start();

        var startTime = System.currentTimeMillis();
        while (executor.isAlive() && ((System.currentTimeMillis()-startTime)<10000)) {
            System.out.printf("%s: executor-thread %s is running since %d ms. %d crawled, %d to go.\n",
                    Thread.currentThread().getName(), executor.getName(), (System.currentTimeMillis()-startTime), sites.getNrCrawledSites(), sites.getNrLinkedSites());
            Thread.sleep(1000);
        }
        executor.interrupt();
        long duration = (System.currentTimeMillis()-startTime);
        System.out.printf("%s: executor-thread %s stopped after %d ms. %d crawled (%f/sec)\n",
                Thread.currentThread().getName(), executor.getName(), duration, sites.getNrCrawledSites(), (float)sites.getNrCrawledSites()*1000.0f/duration);
    }
}
