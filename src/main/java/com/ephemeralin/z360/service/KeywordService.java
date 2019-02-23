package com.ephemeralin.z360.service;

import com.ephemeralin.z360.model.KeywordSet;
import com.ephemeralin.z360.model.SOURCE;

import java.time.LocalDate;

/**
 * The interface KeywordSet service.
 */
public interface KeywordService extends Service<KeywordSet> {
    KeywordSet findByDate(LocalDate date, SOURCE source);
}
