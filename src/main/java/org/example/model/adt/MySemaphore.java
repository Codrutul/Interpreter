package org.example.model.adt;

import org.example.exception.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MySemaphore implements MyISemaphore<Integer, SemaphoreEntry> {
    private final HashMap<Integer, SemaphoreEntry> table;
    private int freeLocation;

    public MySemaphore() {
        this.table = new HashMap<>();
        this.freeLocation = 1;
    }

    @Override
    public synchronized int add(SemaphoreEntry value) throws MyException {
        int addr = freeLocation;
        table.put(addr, value);
        freeLocation++;
        return addr;
    }

    @Override
    public synchronized Optional<SemaphoreEntry> lookup(Integer key) throws MyException {
        return Optional.ofNullable(table.get(key));
    }

    @Override
    public synchronized void update(Integer key, SemaphoreEntry value) throws MyException {
        if (!table.containsKey(key)) throw new MyException("semaphore index " + key + " is not in the table");
        table.put(key, value);
    }

    @Override
    public synchronized boolean isDefined(Integer key) {
        return table.containsKey(key);
    }

    @Override
    public synchronized Map<Integer, SemaphoreEntry> getContent() {
        return table;
    }

    @Override
    public synchronized void setContent(Map<Integer, SemaphoreEntry> content) {
        table.clear();
        table.putAll(content);
    }

    @Override
    public String toString() {
        return table.toString();
    }
}

