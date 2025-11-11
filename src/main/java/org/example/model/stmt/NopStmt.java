package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return state;
    }

    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
