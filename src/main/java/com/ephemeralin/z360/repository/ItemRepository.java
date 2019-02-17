package com.ephemeralin.z360.repository;

import com.ephemeralin.z360.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * The interface Body repository.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByTitle(String title);
    List<Item> findItemsByPubDateBetweenOrderByPubDateDesc(LocalDateTime startDate, LocalDateTime endDate);
    List<Item> findAllByOrderByPubDateDesc();
}
