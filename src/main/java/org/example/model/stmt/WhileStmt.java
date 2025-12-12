package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
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
        return "while(" + exp.toString() + ") do(" + stmt.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = exp.eval(state.getSymTable(), state.getHeap());
        if (!(val instanceof BoolValue)) {
            throw new MyException("Conditional expression is not a boolean");
        }
        BoolValue b = (BoolValue) val;
        MyIStack<IStmt> stk = state.getStk();
        if (b.getVal()) {
            stk.push(this);
            stk.push(stmt);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }
}
