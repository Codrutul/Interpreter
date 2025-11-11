package org.example.repository;

import org.example.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgStateList;

    public Repository(PrgState prgState) {
        this.prgStateList = new ArrayList<>();
        this.prgStateList.add(prgState);
    }

    @Override
    public PrgState getCrtPrg() {
        return prgStateList.get(0);
    }
}
