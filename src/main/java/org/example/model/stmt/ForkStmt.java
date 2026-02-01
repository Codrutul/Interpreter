package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.*;
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

        // clone the entire stack of symbol tables
        java.util.Stack<MyIDictionary<String, Value>> oldStack = state.getSymTableStack();
        MyIStack<MyIDictionary<String, Value>> tmpStack = new MyStack<>();
        // copy elements to tmp to preserve order
        for (MyIDictionary<String, Value> dict : oldStack) {
            tmpStack.push(dict.deepCopy());
        }
        // create a new stack (java.util.Stack) for the child
        java.util.Stack<MyIDictionary<String, Value>> newSymStack = new java.util.Stack<>();
        while (!tmpStack.isEmpty()) {
            newSymStack.push(tmpStack.pop());
        }

        // shared structures
         MyIList<Value> out = state.getOut();
         MyIFileTable<String, BufferedReader> fileTable = state.getFileTable();
         MyIHeap<Integer, Value> heap = state.getHeap();
         MyIProcTable procTable = state.getProcTable();

        // create new PrgState (child thread) using the copied stack
        return new PrgState(newStack, newSymStack, out, fileTable, heap, procTable, forkedStmt.deepCopy());
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
