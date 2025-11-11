package org.example.model.adt;

import org.example.exception.MyException;
import java.util.List;

public interface MyIList<T> {
    void add(T v);
    List<T> getList();
    String toFileString();
    MyIList<T> deepCopy() throws MyException;
}
