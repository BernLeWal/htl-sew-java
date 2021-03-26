package core.threads.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class WebCrawler implements Runnable {
    private final WebCrawlerSites sites;

    public WebCrawler(WebCrawlerSites sites) {
        this.sites = sites;
    }

    @Override
    public void run() {
        var pattern = Pattern.compile("( href=\"http.*\")");

        String currentSite;
        while ( (currentSite=sites.next())!=null && !Thread.interrupted() ) {
            System.out.println(currentSite);

            String content;
            try {
                content = new String(new URL(currentSite).openConnection().getInputStream().readAllBytes());
                var matcher = pattern.matcher( content );
                while (matcher.find() ) {
                    String nextSite = matcher.group();
                    nextSite = nextSite.substring(7, nextSite.indexOf('"',8) );
                    sites.add(nextSite);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
