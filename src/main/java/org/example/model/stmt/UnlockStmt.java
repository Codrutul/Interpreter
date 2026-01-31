package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyILockTable;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

import java.util.Optional;

public class UnlockStmt implements IStmt {
    private final String var;

    public UnlockStmt(String var) { this.var = var; }

    @Override
    public String toString() { return "unlock(" + var + ")"; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(var)) throw new MyException("unlock: var not defined");
        Value v = state.getSymTable().lookup(var);
        if (!(v instanceof IntValue)) throw new MyException("unlock: var is not int");
        int foundIndex = ((IntValue) v).getVal();
        MyILockTable<Integer, Integer> lock = state.getLockTable();
        Optional<Integer> valOpt = lock.lookup(foundIndex);
        if (!valOpt.isPresent()) throw new MyException("unlock: index not in lock table");
        Integer val = valOpt.get();
        int id = state.getId();
        if (val.equals(id)) {
            lock.update(foundIndex, -1);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() { return new UnlockStmt(var); }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = typeEnv.lookup(var);
        if (!t.equals(new IntType())) throw new MyException("unlock: var not int");
        return typeEnv;
    }
}

