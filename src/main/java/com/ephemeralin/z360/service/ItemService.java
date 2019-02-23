package com.ephemeralin.z360.service;

import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.model.SOURCE;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Item service.
 */
public interface ItemService extends Service<Item> {
    Item findByTitle(String title, SOURCE source);
    List<Item> findItemsByPubDateBetween(LocalDateTime startDate, LocalDateTime endDate, SOURCE source);
}
