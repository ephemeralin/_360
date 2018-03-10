package com.ephemeralin.z360.meduza;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Rss grabber.
 */
public class RssGrabber {

    /**
     * Logger instance.
     */
    private Logger logger;

    /**
     * Instantiates a new RSS grabber.
     */
    public RssGrabber() {
        this.logger = LogManager.getLogger(this.getClass());
    }

    /**
     * Grab.
     *
     * @return the list of items
     */
    public List<Item> grab() {
        final ArrayList<Item> items = new ArrayList<>();
        String url = "https://meduza.io/rss/all";
        try {
            final Document doc = Jsoup.connect(url).get();
            final Elements itemElements = doc.getElementsByTag("item");
            for (Element itemElement : itemElements) {
                final Item item = new Item(itemElement.getElementsByTag("title").text(),
                        itemElement.getElementsByTag("link").text(),
                        itemElement.getElementsByTag("description").text(),
                        itemElement.getElementsByTag("pubDate").text(),
                        grabItemsFullText(itemElement.getElementsByTag("link").text()));
                items.add(item);
            }
        } catch (IOException e) {
            logger.error("Error when trying to parse RSS feed", e);
        }
        return items;
    }

    /**
     * Grab full text of the item.
     * @param url the target item url
     * @return full text
     */
    private String grabItemsFullText(String url) {
        String text = "";
        try {
            final Document doc = Jsoup.connect(url).get();
            int i = 1;
        } catch (IOException e) {
            logger.error("Error when trying to parse News page", e);
        }
        return text;
    }
}
