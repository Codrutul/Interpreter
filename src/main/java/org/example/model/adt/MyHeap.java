package org.example.model.adt;

import org.example.exception.MyException;
import org.example.model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap<Integer, Value> {
    private final HashMap<Integer, Value> heap;
    private int freeLocation;

    public MyHeap() {
        this.heap = new HashMap<>();
        this.freeLocation = 1; // addresses start at 1
    }

    @Override
    public int add(Integer ignoredKey, Value value) throws MyException {
        int addr = freeLocation;
        heap.put(addr, value);
        freeLocation++;
        return addr;
    }

    @Override
    public Value lookup(Integer key) throws MyException {
        if (!heap.containsKey(key)) throw new MyException("address " + key + " is not in the heap");
        return heap.get(key);
    }

    @Override
    public void update(Integer key, Value value) throws MyException {
        if (!heap.containsKey(key)) throw new MyException("address " + key + " is not in the heap");
        heap.put(key, value);
    }

    @Override
    public boolean isDefined(Integer key) {
        return heap.containsKey(key);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, Value> content) {
        heap.clear();
        heap.putAll(content);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
