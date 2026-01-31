package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyILockTable;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class NewLockStmt implements IStmt {
    private final String var;

    public NewLockStmt(String var) { this.var = var; }

    @Override
    public String toString() { return "newLock(" + var + ")"; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value v = state.getSymTable().lookup(var);
        if (!(v.getType() instanceof IntType)) throw new MyException("newLock: var is not int");
        MyILockTable<Integer, Integer> lock = state.getLockTable();
        int addr = lock.add(-1);
        state.getSymTable().update(var, new IntValue(addr));
        return null;
    }

    @Override
    public IStmt deepCopy() { return new NewLockStmt(var); }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = typeEnv.lookup(var);
        if (!t.equals(new IntType())) throw new MyException("newLock: var not int");
        return typeEnv;
    }
}

