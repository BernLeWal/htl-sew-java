package core.threads.webcrawler;

public interface Sites {
    /**
     * Adds a site to be crawled.
     * If the given site was already crawled, then it is ignored and not added.
     * @param site
     */
    void add(String site);

    /**
     * Returns the next site to be crawled.
     * If there are no sites available, it immediately returns null.
     * @return
     */
    String next();

    /**
     * Returns the next site to be crawled.
     * If no sites are available, it waits until a new item was added. (blocking call)
     * @return
     * @throws InterruptedException
     */
    String take() throws InterruptedException;

    int getNrCrawledSites();
    int getNrLinkedSites();
}
