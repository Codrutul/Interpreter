package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
import org.example.model.adt.MyStack;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIList;
import org.example.model.adt.MyIFileTable;
import org.example.model.adt.MyIHeap;
import org.example.model.type.Type;
import org.example.model.value.Value;

import java.io.BufferedReader;

public class ForkStmt implements IStmt {
    private final IStmt forkedStmt;

    public ForkStmt(IStmt s) { this.forkedStmt = s; }

    @Override
    public String toString() {
        return "fork(" + forkedStmt.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>();

        // clone the symbol table
        MyIDictionary<String, Value> newSymTable = state.getSymTable().deepCopy();

        // shared structures
        MyIList<Value> out = state.getOut();
        MyIFileTable<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        // create new PrgState (child thread)
        return new PrgState(newStack, newSymTable, out, fileTable, heap, forkedStmt.deepCopy());
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(forkedStmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        forkedStmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
