package com.ephemeralin.z360.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The Item. Piece of news.
 */
@Entity
@Table(name = "items", schema = "public")
@JsonPropertyOrder(value = {"id", "createdDate", "pubDate", "source", "title", "description", "fullText", "link"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Title.
     */
    @Column(name = "title")
    private String title;
    /**
     * Link.
     */
    @Column(name = "link")
    private String link;
    /**
     * Description.
     */
    @Column(name = "description")
    private String description;
    /**
     * Full text of item.
     */
    @Column(name = "full_text")
    private String fullText;

    @Column(name = "pub_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pubDate;

    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Column(name = "source", length = 20)
    @Enumerated(EnumType.STRING)
    private Source source;

    public Item() {
    }

    public Item(String title, String link, String description, String fullText, LocalDateTime pubDate, LocalDateTime createdDate, Source source) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.fullText = fullText;
        this.pubDate = pubDate;
        this.createdDate = createdDate;
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public LocalDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
