package org.example.model;

import org.example.exception.MyException;
import org.example.model.adt.*;
import org.example.model.stmt.IStmt;
import org.example.model.value.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIFileTable<String, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;
    private IStmt originalProgram; //optional field, but good to have

    private MyISemaphore<Integer, SemaphoreEntry> semaphoreTable;

    private final int id;
    private static int lastId = 0;

    private static synchronized int generateId() {
        lastId++;
        return lastId;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIFileTable<String, BufferedReader> fileTable, MyIHeap<Integer, Value> heap, IStmt prg) {
        this(stk, symtbl, ot, fileTable, heap, prg, new MySemaphore());
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIFileTable<String, BufferedReader> fileTable, MyIHeap<Integer, Value> heap, IStmt prg, MyISemaphore<Integer, SemaphoreEntry> sem) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        this.semaphoreTable = sem;
        originalProgram = prg.deepCopy(); //recreate the entire original prg
        stk.push(prg);
        this.id = generateId();
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

    public MyIFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, Value> getHeap() { return heap; }

    public MyISemaphore<Integer, org.example.model.adt.SemaphoreEntry> getSemaphoreTable() { return semaphoreTable; }

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
                "Symbol Table: " + symTable.toString() + "\n" +
                "Out: " + out.toString() + "\n" +
                "FileTable: " + fileTable.toString() + "\n" +
                "Heap: " + heap.toString() + "\n" +
                "SemaphoreTable: " + semaphoreTable.toString() + "\n";
    }

    public String toFileString() {
        return "Id=" + id + "\n" +
                "ExeStack:\n" + exeStack.toFileString() +
                "SymTable:\n" + symTable.toFileString() +
                "Out:\n" + out.toFileString() +
                "FileTable:\n" + fileTable.toFileString() +
                "Heap:\n" + heap.toString() + "\n" +
                "SemaphoreTable:\n" + semaphoreTable.toString() + "\n";
    }
}
