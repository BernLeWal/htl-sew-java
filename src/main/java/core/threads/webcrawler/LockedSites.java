package core.threads.webcrawler;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WebCrawlerSite represents the data structure for a web crawler.
 * It keeps track of the visited sites and keeps a list of sites which needs still to be crawled.
 */
public class LockedSites implements Sites {
    private final Set<String> crawledSites = new HashSet<>();
    private final List<String> linkedSites = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    @Override
    public void add(String site) {
        lock.lock();
        try {
            if (!crawledSites.contains(site)) {
                linkedSites.add(site);
                notEmpty.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get next site to crawl. Can return null (if nothing to crawl)
     */
    @Override
    public String next() {
        if (linkedSites.size() == 0) {
            return null;
        }
        lock.lock();
        try {
            // Need to check again if size has changed
            if (linkedSites.size() > 0) {
                String s = linkedSites.get(0);
                linkedSites.remove(0);
                crawledSites.add(s);
                return s;
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * Get next site to crawl. If there is currently none, wait for one (blocking method-call).
     */
    @Override
    public String take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (linkedSites.size() == 0)
                notEmpty.await();
            return next();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getNrCrawledSites() {
        return crawledSites.size();
    }

    @Override
    public int getNrLinkedSites() {
        return linkedSites.size();
    }
}