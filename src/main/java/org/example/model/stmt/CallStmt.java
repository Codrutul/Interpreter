package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIStack;
import org.example.model.adt.MyIProcTable;
import org.example.model.exp.Exp;
import org.example.model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class CallStmt implements IStmt {
    private final String name;
    private final List<Exp> args;

    public CallStmt(String name, List<Exp> args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public String toString() {
        return "call " + name + "(" + args.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIProcTable pt = state.getProcTable();
        if (!pt.isDefined(name)) {
            String ptContents = "(empty)";
            try { ptContents = pt.toFileString(); } catch (Exception ignored) {}
            throw new MyException("Procedure not found: " + name + "; ProcTable content:\n" + ptContents);
        }
        // get procedure
        org.example.model.adt.Procedure proc = pt.lookup(name);
        List<String> formals = proc.getParams();
        if (formals.size() != args.size()) throw new MyException("Wrong number of arguments for " + name);

        // evaluate args using current symtable and heap
        List<Value> evaluated = new ArrayList<>();
        for (Exp e : args) evaluated.add(e.eval(state.getSymTable(), state.getHeap()));

        // create a fresh local symbol table for the call containing only the formal->argument bindings
        MyIDictionary<String, Value> local = new org.example.model.adt.MyDictionary<>();
        for (int i = 0; i < formals.size(); i++) {
            local.add(formals.get(i), evaluated.get(i));
        }

        // push local symtable onto stack
        state.getSymTableStack().push(local);

        // push return then body
        MyIStack<IStmt> stk = state.getStk();
        stk.push(new ReturnStmt());
        stk.push(proc.getBody().deepCopy());
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CallStmt(name, new ArrayList<>(args));
    }

    @Override
    public MyIDictionary<String, org.example.model.type.Type> typecheck(MyIDictionary<String, org.example.model.type.Type> typeEnv) throws MyException {
        // no static type checking for procedure calls here
        return typeEnv;
    }
}
