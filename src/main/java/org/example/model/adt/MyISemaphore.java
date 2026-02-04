package org.example.model.adt;

import org.example.exception.MyException;

import java.util.Map;
import java.util.Optional;

public interface MyISemaphore<K, V> {
    int add(V value) throws MyException; // returns new index

    Optional<V> lookup(K key) throws MyException;

    void update(K key, V value) throws MyException;

    boolean isDefined(K key);

    Map<K, V> getContent();

    void setContent(Map<K, V> content);
}

