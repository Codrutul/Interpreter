package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.type.BoolType;
import org.example.model.type.Type;

public class CondAssignStmt implements IStmt {
    private final String v;
    private final Exp exp1;
    private final Exp exp2;
    private final Exp exp3;

    public CondAssignStmt(String v, Exp exp1, Exp exp2, Exp exp3) {
        this.v = v;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt ifStmt = new IfStmt(exp1, new AssignStmt(v, exp2), new AssignStmt(v, exp3));
        state.getStk().push(ifStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CondAssignStmt(v, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public String toString() {
        return v + "=(" + exp1.toString() + ")?" + exp2.toString() + ":" + exp3.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(v);
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        Type type3 = exp3.typecheck(typeEnv);

        if (type1.equals(new BoolType())) {
            if (typeVar.equals(type2) && typeVar.equals(type3)) {
                return typeEnv;
            } else {
                throw new MyException("The types of v, exp2, and exp3 must be the same");
            }
        } else {
            throw new MyException("The condition must be of type bool");
        }
    }
}
