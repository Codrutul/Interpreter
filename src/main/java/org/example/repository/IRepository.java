package org.example.repository;

import org.example.exception.MyException;
import org.example.model.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void logPrgStateExec() throws MyException;
}
