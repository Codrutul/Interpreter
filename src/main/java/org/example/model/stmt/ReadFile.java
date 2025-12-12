package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIFileTable;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.exp.Exp;
import org.example.model.value.IntValue;
import org.example.model.value.StringValue;
import org.example.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private final Exp exp;
    private final String varName;

    public ReadFile(Exp e, String varName) {
        this.exp = e;
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + "," + varName + ")";
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
            String line = br.readLine();
            int val = 0;
            if (line != null) {
                val = Integer.parseInt(line);
            }
            if (symTable.isDefined(varName)) {
                symTable.update(varName, new IntValue(val));
            } else {
                throw new MyException("Variable " + varName + " is not declared");
            }
        } catch (IOException e) {
            throw new MyException("Error reading file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }
}
