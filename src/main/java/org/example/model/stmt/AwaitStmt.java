package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class AwaitStmt implements IStmt {
    private String var;

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(var))
            throw new MyException("Variable " + var + " is not defined in SymTable!");
        
        Value foundIndexVal = state.getSymTable().lookup(var);
        if (!foundIndexVal.getType().equals(new IntType()))
            throw new MyException("Variable " + var + " must be of type int!");
            
        int foundIndex = ((IntValue)foundIndexVal).getVal();
        
        synchronized (state.getLatchTable()) {
            if (!state.getLatchTable().containsKey(foundIndex))
                throw new MyException("Index " + foundIndex + " is not in LatchTable!");
            
            if (state.getLatchTable().get(foundIndex) != 0)
                state.getStk().push(this);
        }
        
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var);
        if (typevar.equals(new IntType())) return typeEnv;
        else throw new MyException("Await: variable is not of type int");
    }
}
