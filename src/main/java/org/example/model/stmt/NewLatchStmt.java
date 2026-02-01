package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class NewLatchStmt implements IStmt {
    private String var;
    private Exp exp;

    public NewLatchStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value num = exp.eval(state.getSymTable(), state.getHeap());
        if (!num.getType().equals(new IntType()))
            throw new MyException("Expression value must be int!");
        
        int number = ((IntValue)num).getVal();
        
        synchronized (state.getLatchTable()) {
            int freeLocation = state.getLatchTable().getFreeAddress();
            state.getLatchTable().put(freeLocation, number);
            
            if (state.getSymTable().isDefined(var)) {
                if (state.getSymTable().lookup(var).getType().equals(new IntType()))
                    state.getSymTable().update(var, new IntValue(freeLocation));
                else
                    throw new MyException("Variable " + var + " is not of type int!");
            } else {
                throw new MyException("Variable " + var + " is not defined!");
            }
        }
        
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(var, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new IntType()) && typexp.equals(new IntType())) return typeEnv;
        else throw new MyException("NewLatch: variable and expression must be of type int");
    }
}
