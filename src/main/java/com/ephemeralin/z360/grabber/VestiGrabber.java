package com.ephemeralin.z360.grabber;

import com.ephemeralin.z360.model.Item;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Log4j2
public class VestiGrabber extends BaseGrabber implements IGrabber {
    @Override
    public List<Item> getData() {
        final ArrayList<Item> items = new ArrayList<>();
        String url = "http://www.vesti.ru/vesti.rss";
        try {
            final Document doc = Jsoup.connect(url).get();
            final Elements itemElements = doc.getElementsByTag("item");
            for (Element itemElement : itemElements) {
                Item item = new Item(
                        itemElement.getElementsByTag("title").text(),
                        itemElement.getElementsByTag("link").text(),
                        itemElement.getElementsByTag("description").text(),
                        itemElement.getElementsByTag("yandex:full-text").text(),
//                        parsePubDate(itemElement.getElementsByTag("pubDate").text()),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );
                items.add(item);
            }
        } catch (IOException e) {
            log.error("Error when trying to parse RSS feed", e);
        }
        return items;
    }

    @Override
    public String getDataWithFullText(String url) {
        return null;
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
}
