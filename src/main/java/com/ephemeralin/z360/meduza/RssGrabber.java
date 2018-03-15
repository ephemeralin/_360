package com.ephemeralin.z360.meduza;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
     * Grab data.
     *
     * @return the list
     */
    public List<Item> grab() {
        final ArrayList<Item> items = new ArrayList<>();
        String url = "https://meduza.io/rss/all";
        try {
            final Document doc = Jsoup.connect(url).get();
            final Elements itemElements = doc.getElementsByTag("item");
            for (Element itemElement : itemElements) {
                items.add(createItem(itemElement.getElementsByTag("title").text(),
                                        itemElement.getElementsByTag("link").text(),
                                        itemElement.getElementsByTag("description").text(),
                                        itemElement.getElementsByTag("pubDate").text()));
            }
        } catch (IOException e) {
            logger.error("Error when trying to parse RSS feed", e);
        }
        return items;
    }

    /**
     * Create item.
     * @param title the title
     * @param link the link
     * @param description the description
     * @param pubDateString the publication date (string)
     * @return item
     */
    private Item createItem(String title, String link, String description, String pubDateString) {
        final Item item = new Item(title,
                                    link,
                                    description,
                                    pubDateString,
                                    Calendar.getInstance().getTimeInMillis());
        item.setFullText(grabItemsFullText(link));
        item.setPubDate(parsePubDate(pubDateString));
        return item;
    }

    /**
     * Convert date string to date.
     * @param pubDateString date string
     * @return date
     */
    private Long parsePubDate(String pubDateString) {
        //todo implement parsing of the string date
        return Long.MIN_VALUE;
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
            text = doc.getElementsByClass("MaterialContent").text();
        } catch (IOException e) {
            logger.error("Error when trying to parse News page", e);
        }
        return text;
    }
}
