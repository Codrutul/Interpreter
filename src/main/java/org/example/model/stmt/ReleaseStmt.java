package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyISemaphore;
import org.example.model.adt.SemaphoreEntry;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReleaseStmt implements IStmt {
    private final String var;

    public ReleaseStmt(String var) { this.var = var; }

    @Override
    public String toString() { return "release(" + var + ")"; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(var)) throw new MyException("release: var not defined");
        Value v = state.getSymTable().lookup(var);
        if (!(v instanceof IntValue)) throw new MyException("release: var is not int");
        int foundIndex = ((IntValue) v).getVal();
        MyISemaphore<Integer, SemaphoreEntry> sem = state.getSemaphoreTable();
        Optional<SemaphoreEntry> entryOpt = sem.lookup(foundIndex);
        if (!entryOpt.isPresent()) throw new MyException("release: index not in semaphore table");
        SemaphoreEntry entry = entryOpt.get();
        List<Integer> list = entry.getList();
        int id = state.getId();
        if (list.contains(id)) {
            List<Integer> newList = new ArrayList<>(list);
            newList.remove((Integer) id);
            sem.update(foundIndex, new SemaphoreEntry(entry.getNr(), newList));
        }
        return null;
    }

    @Override
    public IStmt deepCopy() { return new ReleaseStmt(var); }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = typeEnv.lookup(var);
        if (!t.equals(new IntType())) throw new MyException("release: var not int");
        return typeEnv;
    }
}

