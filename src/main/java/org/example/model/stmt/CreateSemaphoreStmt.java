package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.adt.MyISemaphore;
import org.example.model.adt.SemaphoreEntry;
import org.example.model.exp.Exp;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

import static java.util.Collections.emptyList;

public class CreateSemaphoreStmt implements IStmt {
    private final String var;
    private final Exp expr;

    public CreateSemaphoreStmt(String var, Exp expr) {
        this.var = var;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var + "," + expr + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = expr.eval(state.getSymTable(), state.getHeap());
        if (!(val instanceof IntValue)) throw new MyException("createSemaphore: expression is not an integer");
        int number = ((IntValue) val).getVal();
        MyISemaphore<Integer, SemaphoreEntry> sem = state.getSemaphoreTable();
        // add new semaphore entry
        int addr = sem.add(new SemaphoreEntry(number, emptyList()));
        // assign addr to var
        if (!state.getSymTable().isDefined(var))
            throw new MyException("createSemaphore: variable " + var + " is not defined");
        Value varVal = state.getSymTable().lookup(var);
        if (!(varVal.getType() instanceof IntType))
            throw new MyException("createSemaphore: variable " + var + " is not of type int");
        state.getSymTable().update(var, new IntValue(addr));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(var, expr.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type tv = typeEnv.lookup(var);
        Type te = expr.typecheck(typeEnv);
        if (!tv.equals(new IntType())) throw new MyException("createSemaphore: var not int");
        if (!te.equals(new IntType())) throw new MyException("createSemaphore: expr not int");
        return typeEnv;
    }
}

