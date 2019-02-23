package com.ephemeralin.z360.service;

import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.model.SOURCE;
import com.ephemeralin.z360.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The Item service for Vesti source.
 */
@Repository("itemService")
@Transactional
public class itemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository repository;

    @Override
    public long create(Item i) {
        return this.repository.save(i).getId();
    }

    @Override
    public Item findById(long id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Item> findAll(SOURCE source) {
        return this.repository.findAll(source);
    }

    @Override
    public Item update(Item i) {
        return this.repository.save(i);
    }

    @Override
    public void delete(long id) {
        this.repository.deleteById(id);
    }

    @Override
    public Item findByTitle(String title, SOURCE source) {
        return this.repository.findByTitleAndSource(title, source).orElse(null);
    }

    @Override
    public List<Item> findItemsByPubDateBetween(LocalDateTime startDate, LocalDateTime endDate, SOURCE source) {
        return this.repository.findAllByPubDateBetween(startDate, endDate, source);
    }

}
