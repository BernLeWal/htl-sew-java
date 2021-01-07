package server.clients;

import java.awt.Image;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * HttpDownloader demonstrates how www ressources can be accessed an downloaded by the help of the URL class.
 */
public class HttpDownloader {
    protected final URL url;

    public HttpDownloader(String url) throws IOException {
        System.out.println("Downloader using url=" + url);
        this.url = new URL(url);

    }

    private URLConnection openConnection() throws IOException {
        // Do you want to download an image, but are u denied access? Well, here is the solution:
        // This user agent is for if the server wants real humans to visit
        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
        // Setting the user agent
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", USER_AGENT);
        return conn;
    }

    public void downloadToConsole() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("downloadToConsole() ERROR: " + e);
        }
    }


    public String downloadToString() {
        StringBuilder html = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line;
            while ((line = input.readLine()) != null) {
                html.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("downloadToString() ERROR: " + e);
        }
        return html.toString();
    }

    /**
     * downloadToFile will copy the data as binary from the server to the target file,
     * so it is also possible to transfer PNG and JPG files
     */
    public void downloadToFile(String targetDirectory) {
        String fileName = url.getFile();
        int lastSlash = fileName.lastIndexOf('/');
        if (lastSlash >= 0)
            fileName = fileName.substring(lastSlash);
        File targetFile = new File(targetDirectory + fileName);
        System.out.println("  download " + url + " to file " + targetFile);

        try (
                BufferedInputStream in = new BufferedInputStream(url.openConnection().getInputStream());
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile))
        ) {
            byte[] buffer = new byte[2048];
            int length;
            // Looping until server finishes
            while ((length = in.read(buffer)) != -1) {
                // Writing data
                out.write(buffer, 0, length);
            }
        } catch (Exception e) {
            System.err.println("downloadToFile() ERROR: " + e);
        }
    }

    public Image downloadToImage() {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            System.err.println("downloadToImage() ERROR: " + e);
        }
        return null;
    }

    public String[] getHtmlImagesUrls() {
        ArrayList<String> imagesUrls = new ArrayList<>();
        String html = downloadToString();

        // search for <img src=...>-Tags (which contain the pictures)
        int imgTagPos = 0;
        while ((imgTagPos = html.toLowerCase().indexOf("<img ", imgTagPos + 1)) > 0) {
            int srcAttrPos = html.toLowerCase().indexOf(" src=\"", imgTagPos);
            if (srcAttrPos > 0) {
                int srcAttrEnd = html.indexOf("\"", srcAttrPos + 6);
                if (srcAttrEnd > srcAttrPos) {
                    String relFileName = html.substring(srcAttrPos + 6, srcAttrEnd);
                    //System.out.println("Relative file name=" + relFileName);
                    imagesUrls.add(getUrlFromRelativePath(relFileName));
                }
            }
        }
        return imagesUrls.toArray(new String[0]);
    }

    public String getUrlFromRelativePath(String relFileName) {
        if (relFileName.toLowerCase().startsWith("http:/") || relFileName.toLowerCase().startsWith("https:/")) {
            return relFileName; // it already was a full URL
        }
        final int lastSlash = url.getPath().lastIndexOf('/');
        return url.getProtocol() + "://" + url.getHost()
                + ((lastSlash < 0) ? url.getPath() : url.getPath().substring(0, lastSlash))
                + "/" + relFileName;
    }

    public static void main(String[] args) throws IOException {
        // do a google-query
        HttpDownloader downloader = new HttpDownloader("https://www.google.com/?q=htl+donaustadt&t=h_&ia=web");
        downloader.downloadToConsole();

        // NASA Astronomy Pic of the day
        downloader = new HttpDownloader("https://apod.nasa.gov/apod/astropix.html");
//        String html = downloader.downloadToString();
//        System.out.println(html);
        downloader.downloadToFile(System.getProperty("user.home") + File.separatorChar);

        String imageUrl = downloader.getHtmlImagesUrls()[0];    // the first image
        System.out.println("Found image url=" + imageUrl);

        HttpDownloader downloaderImg = new HttpDownloader(imageUrl);
        downloaderImg.downloadToFile(System.getProperty("user.home") + File.separatorChar);
    }

}
