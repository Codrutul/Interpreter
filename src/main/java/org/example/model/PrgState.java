package org.example.model;

import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIList;
import org.example.model.adt.MyIStack;
import org.example.model.stmt.IStmt;
import org.example.model.value.Value;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt originalProgram; //optional field, but good to have

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IStmt prg) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg; //recreate the entire original prg
        stk.push(prg);
    }

    public MyIStack<IStmt> getStk() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "Execution Stack: " + exeStack.toString() + "\n" +
                "Symbol Table: " + symTable.toString() + "\n" +
                "Output: " + out.toString() + "\n";
    }
}
