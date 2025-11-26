package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
}
