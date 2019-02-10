package com.ephemeralin.z360.model;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The Item. Piece of news.
 */
@Data
@Entity
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_id_seq")
//    @SequenceGenerator(
//            name = "items_id_seq",
//            sequenceName = "items_id_seq"
//            allocationSize = 20
//    )
    @Column(name = "id")
    private int id;

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
    private LocalDateTime pubDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Item() {
    }

    public Item(String title, String link, String description, String fullText, LocalDateTime pubDate, LocalDateTime createdDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.fullText = fullText;
        this.pubDate = pubDate;
        this.createdDate = createdDate;
    }
}
