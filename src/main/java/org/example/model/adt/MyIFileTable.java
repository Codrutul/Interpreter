package org.example.model.adt;

import org.example.exception.MyException;

import java.io.BufferedReader;

public interface MyIFileTable<T, V> extends MyIDictionary<T, V> {
    void add(T v1, V v2) throws MyException;
}