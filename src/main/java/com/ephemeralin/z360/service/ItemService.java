package com.ephemeralin.z360.service;

import com.ephemeralin.z360.model.Item;

/**
 * The interface Item service.
 */
public interface ItemService extends Service<Item> {
    Item findByTitle(String title);
}
