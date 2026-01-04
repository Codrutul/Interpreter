package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIStack;
import org.example.model.type.Type;

public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt snd;

    public CompStmt(IStmt f, IStmt s) {
        this.first = f;
        this.snd = s;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getStk().push(snd);
        state.getStk().push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), snd.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return snd.typecheck(first.typecheck(typeEnv));
    }
}
