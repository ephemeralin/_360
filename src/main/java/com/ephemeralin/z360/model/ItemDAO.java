package com.ephemeralin.z360.model;

import com.ephemeralin.z360.meduza.Item;

import java.util.List;

/**
 * The interface Item dao.
 */
public interface ItemDAO {
    /**
     * Find all list.
     *
     * @return the list
     */
    List<Item> findAll();

    /**
     * Find by link.
     *
     * @return the list
     */
    List<Item> findByLink();

    /**
     * Find by title list.
     *
     * @return the list
     */
    List<Item> findByTitle();

    /**
     * Insert item boolean.
     *
     * @param item the item
     * @return the boolean
     */
    boolean insertItem(Item item);

    /**
     * Update item boolean.
     *
     * @param item the item
     * @return the boolean
     */
    boolean updateItem(Item item);

    /**
     * Delete item boolean.
     *
     * @param item the item
     * @return the boolean
     */
    boolean deleteItem(Item item);
}
