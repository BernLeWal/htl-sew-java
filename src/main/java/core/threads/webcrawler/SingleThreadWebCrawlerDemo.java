package core.threads.webcrawler;

public class SingleThreadWebCrawlerDemo {
    public static void main(String[] args) throws InterruptedException {
        var sites = new WebCrawlerSites();
        sites.add("https://www.orf.at");

        WebCrawler worker = new WebCrawler( sites );
        Thread executor = new Thread( worker );
        executor.start();

        var startTime = System.currentTimeMillis();
        while (executor.isAlive() && ((System.currentTimeMillis()-startTime)<10000)) {
            System.out.printf("Thread is running since %d ms. %d crawled, %d to go.\n", (System.currentTimeMillis()-startTime), sites.getNrCrawledSites(), sites.getNrLinkedSites());
            Thread.sleep(1000);
        }
        executor.interrupt();
        long duration = (System.currentTimeMillis()-startTime);
        System.out.printf("Thread stopped after %d ms. %d crawled (%f/sec)\n", duration, sites.getNrCrawledSites(), (float)sites.getNrCrawledSites()*1000.0f/duration);
    }
}
