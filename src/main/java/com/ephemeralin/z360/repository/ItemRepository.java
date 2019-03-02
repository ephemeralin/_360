package com.ephemeralin.z360.repository;

import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * The interface Item repository.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByTitleAndSource(String title, Source source);

    @Query("select i from Item i where i.source = ?3 and i.pubDate between ?1 and ?2 order by i.pubDate desc")
    List<Item> findAllByPubDateBetween(LocalDateTime startDate, LocalDateTime endDate, Source source);

    @Query("select i from Item i where i.source = ?1 order by i.pubDate desc")
    List<Item> findAll(Source source);
}
