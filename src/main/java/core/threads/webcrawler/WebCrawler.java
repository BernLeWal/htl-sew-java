package core.threads.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * WebCrawler is a runnable which retrieves its workload from the WebCrawlerSites collection,
 * it takes a url to a webpage, fetches the content and finds links to other pages which
 * are added to the WebCrawlerSites collection.
 */
public class WebCrawler implements Runnable {
    private static int WEBCRAWLER_COUNT = 0;

    private final int nr;
    private final Sites sites;
    private String currentSite;

    public WebCrawler(Sites sites) throws InterruptedException {
        nr = ++WEBCRAWLER_COUNT;
        System.out.printf("%s Created WebCrawler%d\n", Thread.currentThread().getName(), nr);
        this.sites = sites;
        this.currentSite = sites.take();    // Wait for the first site to start
        System.out.printf("%s Created WebCrawler%d will start with %s\n", Thread.currentThread().getName(), nr, currentSite);
    }

    @Override
    public void run() {
        System.out.printf("%s Started WebCrawler%d...\n", Thread.currentThread().getName(), nr);
        var pattern = Pattern.compile("(a href=\"http.*\")");

        do {
            System.out.printf("%s WebCrawler%d crawling %s\n", Thread.currentThread().getName(), nr, currentSite);

            String content;
            try {
                content = new String(new URL(currentSite).openConnection().getInputStream().readAllBytes());
                var matcher = pattern.matcher( content );
                while (matcher.find() ) {
                    String nextSite = matcher.group();
                    nextSite = nextSite.substring(8, nextSite.indexOf('"',9) );
                    sites.add(nextSite);
                }
            } catch (IOException e) {
                System.err.printf("%s WebCrawler%d error %s\n", Thread.currentThread().getName(), nr, e.getMessage());
            }
        }
        while ( (currentSite=sites.next())!=null && !Thread.interrupted() );

        System.out.printf("%s Finished WebCrawler%d.\n", Thread.currentThread().getName(), nr);
    }

}
