package org.example.model;

import org.example.model.adt.MyIFileTable;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIList;
import org.example.model.adt.MyIStack;
import org.example.model.stmt.IStmt;
import org.example.model.value.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIFileTable<String, BufferedReader> fileTable;
    private IStmt originalProgram; //optional field, but good to have

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIFileTable<String, BufferedReader> fileTable, IStmt prg) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        this.fileTable = fileTable;
        originalProgram = prg.deepCopy(); //recreate the entire original prg
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

    public MyIFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        return "ExeStack: " + exeStack.toString() + "\n" +
                "Symbol Table: " + symTable.toString() + "\n" +
                "Out: " + out.toString() + "\n" +
                "FileTable: " + fileTable.toString() + "\n";
    }

    public String toFileString() {
        return "ExeStack:\n" + exeStack.toFileString() +
                "SymTable:\n" + symTable.toFileString() +
                "Out:\n" + out.toFileString() +
                "FileTable:\n" + fileTable.toFileString();
    }
}
