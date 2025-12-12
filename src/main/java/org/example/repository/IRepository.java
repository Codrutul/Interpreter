package org.example.repository;

import org.example.exception.MyException;
import org.example.model.PrgState;

import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    void logPrgStateExec(PrgState state) throws MyException;
}
