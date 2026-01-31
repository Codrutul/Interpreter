package org.example.model.adt;

import org.example.exception.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MyLockTable implements MyILockTable<Integer, Integer> {
    private final HashMap<Integer, Integer> table;
    private int freeLocation;

    public MyLockTable() {
        this.table = new HashMap<>();
        this.freeLocation = 1;
    }

    @Override
    public synchronized int add(Integer value) throws MyException {
        int addr = freeLocation;
        table.put(addr, value);
        freeLocation++;
        return addr;
    }

    @Override
    public synchronized Optional<Integer> lookup(Integer key) throws MyException {
        return Optional.ofNullable(table.get(key));
    }

    @Override
    public synchronized void update(Integer key, Integer value) throws MyException {
        if (!table.containsKey(key)) throw new MyException("lock index " + key + " is not in the table");
        table.put(key, value);
    }

    @Override
    public synchronized boolean isDefined(Integer key) {
        return table.containsKey(key);
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return table;
    }

    @Override
    public synchronized void setContent(Map<Integer, Integer> content) {
        table.clear();
        table.putAll(content);
    }

    @Override
    public String toString() {
        return table.toString();
    }
}

