package com.ephemeralin.z360.grabber;


import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.model.Source;
import com.ephemeralin.z360.util.DatesHelper;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Rss grabber.
 */
@Log4j2
public class MeduzaGrabber extends BaseGrabber implements IGrabber {
    /**
     * Grab data.
     *
     * @return the list
     */
    @Override
    public List<Item> getData() {
        final ArrayList<Item> items = new ArrayList<>();
        String url = "https://meduza.io/rss/all";
        try {
            final Document doc = Jsoup.connect(url).get();
            final Elements itemElements = doc.getElementsByTag("item");
            for (Element itemElement : itemElements) {
                Item item = new Item(
                        itemElement.getElementsByTag("title").text(),
                        itemElement.getElementsByTag("link").text(),
                        itemElement.getElementsByTag("description").text(),
                        getDataWithFullText(itemElement.getElementsByTag("link").text()),
                        parseDate(itemElement.getElementsByTag("pubDate").text()),
                        LocalDateTime.now(),
                        Source.meduza
                );
                items.add(item);
            }
        } catch (IOException e) {
            log.error("Error when trying to parse RSS feed", e);
        }
        return items;
    }

    @Override
    public LocalDateTime parseDate(String s) {
        return DatesHelper.parseUsingFormatRFC1123(s);
    }

    /**
     * Grab full text of the item.
     * @param url the target item url
     * @return full text
     */
    @Override
    public String getDataWithFullText(String url) {
        String text = "";
        try {
            final Document doc = Jsoup.connect(url).get();
            text = doc.getElementsByClass("MaterialContent").text();
        } catch (IOException e) {
            log.error("Error when trying to parse News page", e);
        }
        return text;
    }
}
