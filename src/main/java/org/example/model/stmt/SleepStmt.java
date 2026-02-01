package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;

public class SleepStmt implements IStmt {
    private final int number;

    public SleepStmt(int number) { this.number = number; }

    @Override
    public String toString() { return "sleep(" + number + ")"; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        if (number == 0) return null;
        stk.push(new SleepStmt(number - 1));
        return null;
    }

    @Override
    public IStmt deepCopy() { return new SleepStmt(number); }

    @Override
    public org.example.model.adt.MyIDictionary<String, org.example.model.type.Type> typecheck(org.example.model.adt.MyIDictionary<String, org.example.model.type.Type> typeEnv) throws MyException {
        return typeEnv;
    }
}

