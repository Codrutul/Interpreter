package org.example.model.adt;

import org.example.exception.MyException;

public class MyFileTable<T, V> extends MyDictionary<T, V> implements MyIFileTable<T, V> {

    public MyFileTable() {
        super();
    }

    @Override
    public void add(T v1, V v2) throws MyException {
        if (isDefined(v1)) {
            throw new MyException("File " + v1 + " is already opened");
        }
        super.add(v1, v2);
    }

    @Override
    public String toFileString() {
        StringBuilder builder = new StringBuilder();
        for (T key : getContent().keySet()) {
            builder.append(key.toString()).append("\n");
        }
        return builder.toString();
    }
}