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
     * Publication date string.
     */
    private String pubDateString;
    /**
     * Publication date.
     */
    private Long pubDate;
    /**
     * Full text of item.
     */
    private String fullText;

    /**
     * Created date.
     */
    private Long createdDate;

    /**
     * Instantiates a new Item.
     *
     * @param title         the title
     * @param link          the link
     * @param description   the description
     * @param pubDateString the pub date string
     * @param createdDate   the createdDate date
     */
    public Item(String title, String link, String description, String pubDateString, Long createdDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDateString = pubDateString;
        this.createdDate = createdDate;
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
     * Gets pub date string.
     *
     * @return the pub date string
     */
    public String getPubDateString() {
        return pubDateString;
    }

    /**
     * Sets pub date string.
     *
     * @param pubDateString the pub date string
     */
    public void setPubDateString(String pubDateString) {
        this.pubDateString = pubDateString;
    }

    /**
     * Gets pub date.
     *
     * @return the pub date
     */
    public Long getPubDate() {
        return pubDate;
    }

    /**
     * Sets pub date.
     *
     * @param pubDate the pub date
     */
    public void setPubDate(Long pubDate) {
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

    /**
     * Gets created date.
     *
     * @return the created date
     */
    public Long getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets created date.
     *
     * @param createdDate the created date
     */
    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", pubDateString='" + pubDateString + '\'' +
                ", pubDate=" + pubDate +
                ", createdDate=" + createdDate +
                '}';
    }
}
