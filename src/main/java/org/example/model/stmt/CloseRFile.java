package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIFileTable;
import org.example.model.adt.MyIDictionary;
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
        Value value = exp.eval(symTable, state.getHeap());
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
        } catch (IOException e) {
            throw new MyException("Error closing file: " + e.getMessage());
        }
        fileTable.remove(fileName);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }
}
