package org.example.model.adt;

import org.example.exception.MyException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    String toFileString();
    MyIStack<T> deepCopy() throws MyException;
}
