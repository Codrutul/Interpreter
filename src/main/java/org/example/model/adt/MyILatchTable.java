package org.example.model.adt;

import org.example.exception.MyException;
import java.util.Map;

public interface MyILatchTable {
    int getFreeAddress();
    void put(int key, int value) throws MyException;
    int get(int key) throws MyException;
    boolean containsKey(int key);
    void update(int key, int value) throws MyException;
    Map<Integer, Integer> getContent();
    void setContent(Map<Integer, Integer> content);
}
