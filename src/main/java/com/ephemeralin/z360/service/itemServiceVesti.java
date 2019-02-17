package com.ephemeralin.z360.service;

import com.ephemeralin.z360.model.Item;
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
@Repository("itemServiceVesti")
@Transactional
public class itemServiceVesti implements ItemService {

    @Autowired
    private ItemRepository repository;

    @Override
    public int create(Item i) {
        return this.repository.save(i).getId();
    }

    @Override
    public Item findById(int id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Item> findAll() {
        return this.repository.findAllByOrderByPubDateDesc();
    }

    @Override
    public Item update(Item i) {
        return this.repository.save(i);
    }

    @Override
    public void delete(int id) {
        this.repository.deleteById(id);
    }

    @Override
    public Item findByTitle(String title) {
        return this.repository.findByTitle(title).orElse(null);
    }

    @Override
    public List<Item> findItemsByPubDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return this.repository.findItemsByPubDateBetweenOrderByPubDateDesc(startDate, endDate);
    }



}
