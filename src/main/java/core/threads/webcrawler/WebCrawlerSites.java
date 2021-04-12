package core.threads.webcrawler;

import java.util.*;

/**
 * WebCrawlerSite represents the data structure for a web crawler.
 * It keeps track of the visited sites and keeps a list of sites which needs still to be crawled.
 *
 * based on https://www.vogella.com/tutorials/JavaConcurrency/article.html
 */
public class WebCrawlerSites {
    private final Set<String> crawledSites = new HashSet<>();
    private final List<String> linkedSites = new LinkedList<>();

    public void add(String site) {
        synchronized (this) {
            if (!crawledSites.contains(site)) {
                linkedSites.add(site);
            }
        }
    }

    /**
     * Get next site to crawl. Can return null (if nothing to crawl)
     */
    public String next() {
        if (linkedSites.size() == 0) {
            return null;
        }
        synchronized (this) {
            // Need to check again if size has changed
            if (linkedSites.size() > 0) {
                String s = linkedSites.get(0);
                linkedSites.remove(0);
                crawledSites.add(s);
                return s;
            }
            return null;
        }
    }

    public int getNrCrawledSites() {
        return crawledSites.size();
    }

    public int getNrLinkedSites() {
        return linkedSites.size();
    }
}