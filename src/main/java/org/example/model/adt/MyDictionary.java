package org.example.model.adt;

import org.example.exception.DictionaryException;
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
    public void update(T1 v1, T2 v2) throws DictionaryException {
        if (!isDefined(v1)) {
            throw new DictionaryException(v1 + " is not defined.");
        }
        dictionary.put(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) throws MyException {
        if (!isDefined(id)) {
            throw new DictionaryException(id + " is not defined.");
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
}
