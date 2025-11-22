package org.example.model.adt;

import org.example.exception.MyException;

import java.util.Map;

public interface MyIHeap<K, V> {
    int add(K key, V value) throws MyException; // allocate new address (key ignored or used)
    V lookup(K key) throws MyException;
    void update(K key, V value) throws MyException;
    boolean isDefined(K key);
    Map<K, V> getContent();
    void setContent(Map<K, V> content);
}

