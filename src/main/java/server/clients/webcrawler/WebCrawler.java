package server.clients.webcrawler;

import server.clients.HttpDownloader;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

/**
 * WebCrawler runs through the tags of the web-page given by the url
 * to collect picture information and download the image files.
 */
public class WebCrawler extends HttpDownloader {

    public WebCrawler(String url) throws IOException {
        super(url);
    }

    public String[] getHtmlLinkUrls() {
        HashSet<String> linkUrls = new HashSet<>();
        String html = downloadToString();
        String htmlLower = html.toLowerCase();

        // search for <a href=...>-Tags (which contain the pictures)
        int linkTagPos = 0;
        while ((linkTagPos = htmlLower.indexOf("<a ", linkTagPos + 1)) > 0) {
            int hrefAttrPos = htmlLower.indexOf(" href=\"", linkTagPos);
            if (hrefAttrPos > 0) {
                int hrefAttrEnd = html.indexOf("\"", hrefAttrPos + 7);
                if (hrefAttrEnd > hrefAttrPos) {
                    String linkUrl = html.substring(hrefAttrPos + 7, hrefAttrEnd);
                    linkUrl = getUrlFromRelativePath(linkUrl);
                    //System.out.println("Link href=" + linkUrl);
                    linkUrls.add(linkUrl);
                }
            }
        }
        return linkUrls.toArray(new String[0]);
    }

    public void followLinks(int depth, Callback callback) {
        try {
            if (callback != null) {
                callback.onEnter(this);
            }

            if (depth > 0) {
                for (String linkUrl : getHtmlLinkUrls()) {
                    //System.out.println("Follow link url=" + linkUrl);
                    WebCrawler webCrawler = new WebCrawler(linkUrl);
                    webCrawler.followLinks(depth - 1, callback);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public interface Callback {

        void onEnter(WebCrawler webCrawler) throws IOException;
    }

    public static void main(String[] args) throws IOException {
        // create destination directory for the image-files
        new File(System.getProperty("user.dir") + "\\downloader\\").mkdir();

        WebCrawler webCrawler = new WebCrawler("https://www.golem.de/index.htm");
        webCrawler.followLinks(1, new Callback() {
            @Override
            public void onEnter(WebCrawler webCrawler) throws IOException {
                for (String imageUrl : webCrawler.getHtmlImagesUrls()) {
                    HttpDownloader downloaderImg = new HttpDownloader(imageUrl);
                    downloaderImg.downloadToFile(System.getProperty("user.dir") + "\\downloader\\");
                }
            }
        }
        );
    }
}
