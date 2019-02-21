package com.ephemeralin.z360.service;

import com.ephemeralin.z360.model.Source;

import java.util.List;

/**
 * The interface Service.
 *
 * @param <T> the type parameter
 */
public interface Service<T> {
    /**
     * Create int.
     *
     * @param entity the entity
     * @return the int
     */
    long create(T entity);

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    T findById(long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll(Source source);

    /**
     * Update t.
     *
     * @param entity the entity
     * @return the t
     */
    T update(T entity);

    /**
     * Delete boolean.
     *
     * @param id the id
     */
    void delete(long id);

}
