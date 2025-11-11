package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
import org.example.model.exp.Exp;
import org.example.model.type.BoolType;
import org.example.model.value.BoolValue;
import org.example.model.value.Value;

public class IfStmt implements IStmt {
    private final Exp exp;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value cond = exp.eval(state.getSymTable());
        if (!cond.getType().equals(new BoolType())) {
            throw new MyException("conditional expr is not a boolean");
        } else {
            MyIStack<IStmt> stk = state.getStk();
            if (((BoolValue) cond).getVal()) {
                stk.push(thenS);
            } else {
                stk.push(elseS);
            }
        }
        return state;
    }
}
