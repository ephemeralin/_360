package com.ephemeralin.z360.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "keywords", schema = "public")
public class KeywordSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "source", length = 20)
    @Enumerated(EnumType.STRING)
    private Source source;

    @Column(name = "words")
    private String words;

    public KeywordSet(long id) {
        this.id = id;
    }

    public KeywordSet() {
    }
}
