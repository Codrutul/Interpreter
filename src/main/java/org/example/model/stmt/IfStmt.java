package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.exp.Exp;
import org.example.model.value.BoolValue;
import org.example.model.value.Value;

public class IfStmt implements IStmt {
    private final Exp exp;
    private final IStmt thenS, elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString() {
        return "IF(" + exp.toString() + ") THEN(" + thenS.toString() + ") ELSE(" + elseS.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value cond = exp.eval(symTable, heap);
        if (cond instanceof BoolValue) {
            boolean b = ((BoolValue) cond).getVal();
            if (b) state.getStk().push(thenS);
            else state.getStk().push(elseS);
        } else {
            throw new MyException("condition exp is not a boolean");
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }
}
