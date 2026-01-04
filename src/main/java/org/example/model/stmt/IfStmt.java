package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.type.BoolType;
import org.example.model.type.Type;
import org.example.model.value.BoolValue;
import org.example.model.value.Value;

public class IfStmt implements IStmt {
    private final Exp exp;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "IF(" + exp.toString() + ")THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value res = exp.eval(state.getSymTable(), state.getHeap());
        if (!(res instanceof BoolValue)) {
            throw new MyException("Conditional expression is not a boolean");
        }
        BoolValue b = (BoolValue) res;
        MyIStack<IStmt> stk = state.getStk();
        if (b.getVal()) stk.push(thenS);
        else stk.push(elseS);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else throw new MyException("The condition of IF has not the type bool");
    }
}
