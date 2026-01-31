package org.example.model.adt;

import java.util.ArrayList;
import java.util.List;

public class SemaphoreEntry {
    private int nr;
    private final List<Integer> list;

    public SemaphoreEntry(int nr, List<Integer> list) {
        this.nr = nr;
        this.list = new ArrayList<>(list);
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public List<Integer> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "(" + nr + "," + list.toString() + ")";
    }
}

