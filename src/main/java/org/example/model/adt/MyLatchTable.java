package org.example.model.adt;

import org.example.exception.MyException;
import java.util.HashMap;
import java.util.Map;

public class MyLatchTable implements MyILatchTable {
    private HashMap<Integer, Integer> latchTable;
    private int freeLocation;

    public MyLatchTable() {
        this.latchTable = new HashMap<>();
        this.freeLocation = 1;
    }

    @Override
    public synchronized int getFreeAddress() {
        freeLocation++;
        return freeLocation - 1;
    }

    @Override
    public synchronized void put(int key, int value) throws MyException {
        latchTable.put(key, value);
    }

    @Override
    public synchronized int get(int key) throws MyException {
        if (!latchTable.containsKey(key))
            throw new MyException(String.format("Latch table doesn't contain key %d", key));
        return latchTable.get(key);
    }

    @Override
    public synchronized boolean containsKey(int key) {
        return latchTable.containsKey(key);
    }

    @Override
    public synchronized void update(int key, int value) throws MyException {
        if (!latchTable.containsKey(key))
            throw new MyException(String.format("Latch table doesn't contain key %d", key));
        latchTable.put(key, value);
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return latchTable;
    }

    @Override
    public synchronized void setContent(Map<Integer, Integer> content) {
        this.latchTable = new HashMap<>(content);
    }

    @Override
    public synchronized String toString() {
        return latchTable.toString();
    }
}
