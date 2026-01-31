package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.type.IntType;
import org.example.model.type.Type;

public class ForStmt implements IStmt {
    private final String var;
    private final Exp exp1;
    private final Exp exp2;
    private final Exp exp3;
    private final IStmt stmt;

    public ForStmt(String var, Exp exp1, Exp exp2, Exp exp3, IStmt stmt) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + exp1 + ";" + var + "<" + exp2 + ";" + var + "=" + exp3 + ")" + stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        // transform into: int v; v=exp1; (while(v<exp2) stmt; v=exp3)
        IStmt decl = new VarDeclStmt(var, new IntType());
        IStmt assignInit = new AssignStmt(var, exp1.deepCopy());
        IStmt whileBody = new CompStmt(stmt.deepCopy(), new AssignStmt(var, exp3.deepCopy()));
        IStmt whileStmt = new WhileStmt(new org.example.model.exp.RelationalExp("<", new org.example.model.exp.VarExp(var), exp2.deepCopy()), whileBody);
        IStmt newStmt = new CompStmt(decl, new CompStmt(assignInit, whileStmt));
        state.getStk().push(newStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        // the loop variable is local to the for; add it to a copied environment so expressions
        // (including exp3) that reference the loop variable can typecheck
        MyIDictionary<String, Type> newEnv = typeEnv.deepCopy();
        newEnv.add(var, new IntType());
        Type t1 = exp1.typecheck(newEnv);
        Type t2 = exp2.typecheck(newEnv);
        Type t3 = exp3.typecheck(newEnv);
        if (!t1.equals(new IntType()) || !t2.equals(new IntType()) || !t3.equals(new IntType())) {
            throw new MyException("ForStmt: all expressions must be integers");
        }
        stmt.typecheck(newEnv);
        return typeEnv;
    }
}
