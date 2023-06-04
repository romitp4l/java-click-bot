import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinkClickCounter {
    public static void main(String[] args) {
        // Replace the URL with the actual webpage URL you want to scrape
        String url = "https://example.com";

        // List of user agents
        List<String> userAgents = new ArrayList<>();
        userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36");
        userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:95.0) Gecko/20100101 Firefox/95.0");
        userAgents.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36");

        try {
            // Randomly select a user agent from the list
            String userAgent = getRandomUserAgent(userAgents);

            // Configure Tor SOCKS proxy
            System.setProperty("socksProxyHost", "127.0.0.1");
            System.setProperty("socksProxyPort", "9050");

            // Connect to the webpage with the selected user agent and Tor proxy
            Connection connection = Jsoup.connect(url).userAgent(userAgent).timeout(5000);
            connection.proxyType(Connection.ProxyType.SOCKS);

            Document doc = connection.get();

            // Replace "a" with the appropriate selector for your link
            Elements links = doc.select("a");

            int totalClicks = 0;

            // Iterate over each link and extract the number of clicks
            for (Element link : links) {
                // Replace "data-clicks" with the appropriate attribute that holds the click count
                String clickCountStr = link.attr("data-clicks");

                if (!clickCountStr.isEmpty()) {
                    int clickCount = Integer.parseInt(clickCountStr);
                    totalClicks += clickCount;
                }
            }

            System.out.println("Total Clicks: " + totalClicks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRandomUserAgent(List<String> userAgents) {
        Random random = new Random();
        int index = random.nextInt(userAgents.size());
        return userAgents.get(index);
    }
}
