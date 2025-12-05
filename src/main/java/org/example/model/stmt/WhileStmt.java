package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.exp.Exp;
import org.example.model.value.BoolValue;
import org.example.model.value.Value;

public class WhileStmt implements IStmt {
    private final Exp exp;
    private final IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "while(" + exp.toString() + ")" + stmt.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value cond = exp.eval(symTable, heap);
        if (!(cond instanceof BoolValue)) {
            throw new MyException("condition exp is not a boolean");
        }
        boolean b = ((BoolValue) cond).getVal();
        if (b) {
            state.getStk().push(this);
            state.getStk().push(stmt);
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }
}
