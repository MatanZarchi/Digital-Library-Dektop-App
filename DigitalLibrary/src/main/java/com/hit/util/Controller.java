package com.hit.util;

import java.util.Collection;

public interface Controller<K, V> {

    V get(V value);
    Collection<V> get();
    void post(V value);


}
