package com.hit.dao;

import com.hit.dm.Book;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public interface IDao<K, V>{

    void save(Collection<V> books) throws IllegalArgumentException;
    Collection<V> load() throws IOException, ClassNotFoundException;

    V find(K key) throws IllegalArgumentException;
    boolean add(V item);
}
