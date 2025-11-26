package org.example.model.adt;

import org.example.exception.MyException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2> {
    private final HashMap<T1, T2> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public void add(T1 v1, T2 v2) {
        dictionary.put(v1, v2);
    }

    @Override
    public void update(T1 v1, T2 v2) throws MyException {
        if (!isDefined(v1)) {
            throw new MyException(v1 + " is not defined.");
        }
        dictionary.put(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) throws MyException {
        if (!isDefined(id)) {
            throw new MyException(id + " is not defined.");
        }
        return dictionary.get(id);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public String toFileString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<T1, T2> entry : dictionary.entrySet()) {
            result.append(entry.getKey().toString()).append(" -> ").append(entry.getValue().toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public MyIDictionary<T1, T2> deepCopy() throws MyException {
        MyIDictionary<T1, T2> newDict = new MyDictionary<>();
        for (Map.Entry<T1, T2> entry : dictionary.entrySet()) {
            newDict.add(entry.getKey(), entry.getValue());
        }
        return newDict;
    }

    @Override
    public void remove(T1 id) throws MyException {
        if (!isDefined(id)) {
            throw new MyException(id + " is not defined.");
        }
        dictionary.remove(id);
    }
}
