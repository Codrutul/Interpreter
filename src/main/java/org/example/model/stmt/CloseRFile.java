package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIFileTable;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.exp.Exp;
import org.example.model.value.StringValue;
import org.example.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private final Exp exp;

    public CloseRFile(Exp e) {
        exp = e;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIFileTable<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value value = exp.eval(symTable, heap);
        if (!(value instanceof StringValue)) {
            throw new MyException("The file name expression is not a string");
        }
        String fileName = ((StringValue) value).getVal();
        if (!fileTable.isDefined(fileName)) {
            throw new MyException("File not opened: " + fileName);
        }
        BufferedReader br = fileTable.lookup(fileName);
        try {
            br.close();
            fileTable.remove(fileName);
        } catch (IOException e) {
            throw new MyException("Error closing file: " + e.getMessage());
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }
}
