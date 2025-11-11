package org.example.model.adt;

import org.example.exception.MyException;

import java.util.Map;

public interface MyIDictionary<T1, T2> {
    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2) throws MyException;
    T2 lookup(T1 id) throws MyException;
    boolean isDefined(T1 id);
    Map<T1, T2> getContent();
    String toFileString();
    MyIDictionary<T1, T2> deepCopy() throws MyException;
    void remove(T1 id) throws MyException;
}
