package com.ephemeralin.z360.meduza;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores an RSS feed.
 */
public class Feed {

    /**
     * The Title.
     */
    private final String title;
    /**
     * The Link.
     */
    private final String link;
    /**
     * The Description.
     */
    private final String description;
    /**
     * The Pub date.
     */
    private final String pubDate;

    /**
     * The Entries.
     */
    private final List<Item> entries = new ArrayList<Item>();

    /**
     * Instantiates a new Feed.
     *
     * @param title       the title
     * @param link        the link
     * @param description the description
     * @param pubDate     the pub date
     */
    public Feed(String title, String link, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public List<Item> getMessages() {
        return entries;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets link.
     *
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Gets pub date.
     *
     * @return the pub date
     */
    public String getPubDate() {
        return pubDate;
    }
}
