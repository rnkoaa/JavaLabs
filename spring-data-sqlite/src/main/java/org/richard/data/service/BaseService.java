package org.richard.data.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by rnkoaa on 6/4/15.
 */
public interface BaseService<T, ID extends Serializable> {

    Optional<T> findOne(ID id);

    List<T> findAll();

    T save(T item);

    T update(T item);

    boolean delete(T item);
}
