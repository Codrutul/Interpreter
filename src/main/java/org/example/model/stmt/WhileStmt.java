package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.type.BoolType;
import org.example.model.type.Type;
import org.example.model.value.BoolValue;
import org.example.model.value.IntValue;
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
        boolean cond;
        if (val instanceof BoolValue) {
            cond = ((BoolValue) val).getVal();
        } else if (val instanceof IntValue) {
            cond = ((IntValue) val).getVal() != 0;
        } else {
            throw new MyException("Conditional expression is not a boolean: value='" + val.toString() + "' (" + val.getClass().getSimpleName() + ")");
        }
        MyIStack<IStmt> stk = state.getStk();
        if (cond) {
            stk.push(this);
            stk.push(stmt);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = exp.typecheck(typeEnv);
        if (t.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else throw new MyException("The condition of WHILE has not the type bool");
    }
}
