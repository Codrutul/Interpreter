package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.EqualExp;
import org.example.model.exp.Exp;
import org.example.model.type.Type;

public class SwitchStmt implements IStmt {
    private final Exp exp;
    private final Exp exp1;
    private final IStmt stmt1;
    private final Exp exp2;
    private final IStmt stmt2;
    private final IStmt defaultStmt;

    public SwitchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt defaultStmt) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.defaultStmt = defaultStmt;
    }

    @Override
    public String toString() {
        return "switch(" + exp + ") (case " + exp1 + ": " + stmt1 + ") (case " + exp2 + ": " + stmt2 + ") (default: " + defaultStmt + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Exp cond1 = new EqualExp(exp.deepCopy(), exp1.deepCopy());
        Exp cond2 = new EqualExp(exp.deepCopy(), exp2.deepCopy());
        IStmt nestedIf = new IfStmt(cond2, stmt2.deepCopy(), defaultStmt.deepCopy());
        IStmt outerIf = new IfStmt(cond1, stmt1.deepCopy(), nestedIf);
        state.getStk().push(outerIf);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(), exp2.deepCopy(), stmt2.deepCopy(), defaultStmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type tExp = exp.typecheck(typeEnv);
        Type tExp1 = exp1.typecheck(typeEnv);
        Type tExp2 = exp2.typecheck(typeEnv);
        if (!tExp.equals(tExp1) || !tExp.equals(tExp2)) throw new MyException("Switch: all expressions must have the same type");
        stmt1.typecheck(typeEnv.deepCopy());
        stmt2.typecheck(typeEnv.deepCopy());
        defaultStmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}

