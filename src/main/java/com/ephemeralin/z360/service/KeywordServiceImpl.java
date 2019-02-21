package com.ephemeralin.z360.service;

import com.ephemeralin.z360.model.KeywordSet;
import com.ephemeralin.z360.model.Source;
import com.ephemeralin.z360.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * The Keywords service.
 */
@Repository("KeywordServiceImpl")
@Transactional
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordRepository repository;

    @Override
    public long create(KeywordSet i) {
        return this.repository.save(i).getId();
    }

    @Override
    public KeywordSet findById(long id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<KeywordSet> findAll(Source source) {
        return this.repository.findAll(source);
    }

    @Override
    public KeywordSet update(KeywordSet i) {
        return this.repository.save(i);
    }

    @Override
    public void delete(long id) {
        this.repository.deleteById(id);
    }

    @Override
    public KeywordSet findByDate(LocalDate date, Source source) {
        return this.repository.findByDate(date.atStartOfDay(), source).orElse(null);
    }



}
