package com.es.phoneshop.dao;

import java.util.Optional;

public interface Dao<T> {
    void save(T t);
    <V> Optional<T> getItem(V v);
}
