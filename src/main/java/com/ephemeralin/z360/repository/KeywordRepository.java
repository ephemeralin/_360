package com.ephemeralin.z360.repository;

import com.ephemeralin.z360.model.KeywordSet;
import com.ephemeralin.z360.model.SOURCE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * The interface KeywordSet repository.
 */
@Repository
public interface KeywordRepository extends JpaRepository<KeywordSet, Long> {
    @Query("select k from KeywordSet k where k.source = ?2 and k.createdDate = ?1")
    Optional<KeywordSet> findByDate(LocalDateTime startDate, SOURCE source);

    @Query("select k from KeywordSet k where k.source = ?1 order by k.createdDate desc")
    List<KeywordSet> findAll(SOURCE source);
}