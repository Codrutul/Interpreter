package org.example.model.adt;

import org.example.exception.MyException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private final List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public String toFileString() {
        StringBuilder result = new StringBuilder();
        for (T elem : list) {
            result.append(elem.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public MyIList<T> deepCopy() throws MyException {
        MyIList<T> newList = new MyList<>();
        for (T elem : list) {
            newList.add(elem);
        }
        return newList;
    }
}
