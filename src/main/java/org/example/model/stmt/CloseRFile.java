package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.type.StringType;
import org.example.model.value.StringValue;
import org.example.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private final Exp exp;

    public CloseRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value value = exp.eval(state.getSymTable());
        if (!value.getType().equals(new StringType())) {
            throw new MyException("File path expression must evaluate to a string.");
        }

        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName.getVal())) {
            throw new MyException("File '" + fileName.getVal() + "' is not open.");
        }

        try {
            fileTable.lookup(fileName.getVal()).close();
            fileTable.remove(fileName.getVal());
        } catch (IOException e) {
            throw new MyException("Error closing file '" + fileName.getVal() + "': " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() { return "closeRFile(" + exp.toString() + ")"; }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }
}
