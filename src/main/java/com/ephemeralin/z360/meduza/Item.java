package com.ephemeralin.z360.meduza;

/**
 * The type Item.
 */
public class Item {

    /**
     * Title.
     */
    private String title;
    /**
     * Link.
     */
    private String link;
    /**
     * Description.
     */
    private String description;
    /**
     * Publication date.
     */
    private String pubDate;
    /**
     * Full text of item.
     */
    private String fullText;

    /**
     * Instantiates a new Item.
     *
     * @param title       the title
     * @param link        the link
     * @param description the description
     * @param pubDate     the pub date
     * @param fullText    the full text
     */
    public Item(String title, String link, String description, String pubDate, String fullText) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.fullText = fullText;
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
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Sets link.
     *
     * @param link the link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets pub date.
     *
     * @return the pub date
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * Sets pub date.
     *
     * @param pubDate the pub date
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * Gets full text.
     *
     * @return the full text
     */
    public String getFullText() {
        return fullText;
    }

    /**
     * Sets full text.
     *
     * @param fullText the full text
     */
    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", fullText='" + fullText + '\'' +
                '}';
    }
}
