package org.example.model.adt;

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
}
