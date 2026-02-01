package org.example.model;

import org.example.exception.MyException;
import org.example.model.adt.MyIFileTable;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIList;
import org.example.model.adt.MyIStack;
import org.example.model.adt.MyIHeap;
import org.example.model.adt.MyIProcTable;
import org.example.model.adt.MyStack;
import java.util.Stack;
import org.example.model.stmt.IStmt;
import org.example.model.value.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    // stack of symbol tables (for procedures)
    private final Stack<MyIDictionary<String, Value>> symTableStack;
    private MyIList<Value> out;
    private MyIFileTable<String, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;
    private MyIProcTable procTable; // shared global proc table
    private IStmt originalProgram; //optional field, but good to have

    private final int id;
    private static int lastId = 0;

    private static synchronized int generateId() {
        lastId++;
        return lastId;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIFileTable<String, BufferedReader> fileTable, MyIHeap<Integer, Value> heap, MyIProcTable procTable, IStmt prg) {
        exeStack = stk;
        symTableStack = new Stack<>();
        symTableStack.push(symtbl); // initial (global) symbol table
        out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        this.procTable = procTable;
        originalProgram = prg.deepCopy(); //recreate the entire original prg
        stk.push(prg);
        this.id = generateId();
    }

    // overload: accept existing symtable stack (used when forking)
    public PrgState(MyIStack<IStmt> stk, Stack<MyIDictionary<String, Value>> symtblStack, MyIList<Value> ot, MyIFileTable<String, BufferedReader> fileTable, MyIHeap<Integer, Value> heap, MyIProcTable procTable, IStmt prg) {
        exeStack = stk;
        this.symTableStack = new Stack<>();
        // copy provided stack into internal stack
        for (MyIDictionary<String, Value> d : symtblStack) {
            this.symTableStack.push(d);
        }
        out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        this.procTable = procTable;
        originalProgram = prg.deepCopy();
        stk.push(prg);
        this.id = generateId();
    }

    public MyIStack<IStmt> getStk() { return exeStack; }

    // return the top (current) symbol table
    public MyIDictionary<String, Value> getSymTable() { return symTableStack.peek(); }

    public Stack<MyIDictionary<String, Value>> getSymTableStack() { return symTableStack; }

    public MyIProcTable getProcTable() { return procTable != null ? procTable : org.example.model.adt.MyProcTable.getShared(); }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, Value> getHeap() { return heap; }

    public int getId() { return id; }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return "Id=" + id + "\n" +
                "ExeStack: " + exeStack.toString() + "\n" +
                "Symbol Table: " + symTableStack.peek().toString() + "\n" +
                "Out: " + out.toString() + "\n" +
                "FileTable: " + fileTable.toString() + "\n" +
                "Heap: " + heap.toString() + "\n";
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id=").append(id).append("\n");
        sb.append("ExeStack:\n").append(exeStack.toFileString());
        sb.append("SymTableStack:\n");
        for (int i = symTableStack.size() - 1; i >= 0; i--) {
            sb.append("Level ").append(i).append(":\n").append(symTableStack.get(i).toFileString());
        }
        sb.append("Out:\n").append(out.toFileString());
        sb.append("FileTable:\n").append(fileTable.toFileString());
        sb.append("Heap:\n").append(heap.toString()).append("\n");
        sb.append("ProcTable:\n").append(procTable.toFileString()).append("\n");
        return sb.toString();
    }
}
