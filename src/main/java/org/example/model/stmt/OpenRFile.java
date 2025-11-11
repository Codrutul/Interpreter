package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.type.StringType;
import org.example.model.value.StringValue;
import org.example.model.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt {
    private final Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value value = exp.eval(state.getSymTable());
        if (!value.getType().equals(new StringType())) {
            throw new MyException("Expression must be a string for opening a file.");
        }

        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (fileTable.isDefined(fileName.getVal())) {
            throw new MyException("File '" + fileName.getVal() + "' is already open.");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName.getVal()));
            fileTable.add(fileName.getVal(), br);
        } catch (IOException e) {
            throw new MyException("Error opening file '" + fileName.getVal() + "': " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() { return "openRFile(" + exp.toString() + ")"; }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }
}