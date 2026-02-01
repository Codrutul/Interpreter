package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;

public class ReturnStmt implements IStmt {
    @Override
    public String toString() { return "return"; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        // pop the top of the symtable stack
        state.getSymTableStack().pop();
        return null;
    }

    @Override
    public IStmt deepCopy() { return new ReturnStmt(); }

    @Override
    public org.example.model.adt.MyIDictionary<String, org.example.model.type.Type> typecheck(org.example.model.adt.MyIDictionary<String, org.example.model.type.Type> typeEnv) throws MyException {
        return typeEnv;
    }
}

