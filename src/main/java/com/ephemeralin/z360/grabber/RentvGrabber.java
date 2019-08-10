package com.ephemeralin.z360.grabber;

import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.model.Source;
import com.ephemeralin.z360.util.DatesHelper;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class RentvGrabber implements IGrabber {

    @Override
    public List<Item> getData() {
        final ArrayList<Item> items = new ArrayList<>();
        try {
            String url = "http://ren.tv/export/feed.xml";
            Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", "", Parser.xmlParser());
            final Elements itemElements = doc.getElementsByTag("item");
            for (Element itemElement : itemElements) {
                String fullText = getDataWithFullText(itemElement.getElementsByTag("link").text());
                Item item = new Item(
                        itemElement.getElementsByTag("title").text(),
                        itemElement.getElementsByTag("link").text(),
                        fullText,
                        fullText,
                        parseDate(itemElement.getElementsByTag("pubDate").text()),
                        LocalDateTime.now(),
                        Source.rentv
                );
                items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public LocalDateTime parseDate(String s) {
        return DatesHelper.parseUsingFormatRFC1123(s);
    }

    @Override
    public String getDataWithFullText(String url) {
        String text = "";
        try {
            final Document doc = Jsoup.connect(url).get();
            text = doc.select("meta[name=description]").get(0).attr("content");
        } catch (IOException | IndexOutOfBoundsException e) {
            log.error("Error when trying to parse News page", e);
        }
        return text;
    }
}
