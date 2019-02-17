package com.ephemeralin.z360.grabber;

import com.ephemeralin.z360.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface IGrabber {
    List<Item> getData();
    LocalDateTime parseDate(String s);
    String getDataWithFullText(String url);
}

/*
Possible sources:
https://meduza.io/rss/all

http://ren.tv/export/feed.xml
http://www.vesti.ru/vesti.rss
http://www.aif.ru/rss/news
 */