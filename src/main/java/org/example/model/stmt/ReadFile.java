package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.type.IntType;
import org.example.model.type.StringType;
import org.example.model.value.IntValue;
import org.example.model.value.StringValue;
import org.example.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private final Exp exp;
    private final String varName;

    public ReadFile(Exp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varName) || !symTable.lookup(varName).getType().equals(new IntType())) {
            throw new MyException("Variable '" + varName + "' is not defined or is not of type int.");
        }

        Value value = exp.eval(symTable);
        if (!value.getType().equals(new StringType())) {
            throw new MyException("File path expression must evaluate to a string.");
        }

        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        BufferedReader br;
        try {
            br = fileTable.lookup(fileName.getVal());
        } catch (MyException e) {
            throw new MyException("File '" + fileName.getVal() + "' is not open.");
        }

        try {
            String line = br.readLine();
            int val = (line == null || line.isEmpty()) ? 0 : Integer.parseInt(line);
            symTable.update(varName, new IntValue(val));
        } catch (IOException | NumberFormatException e) {
            throw new MyException("Error reading from file '" + fileName.getVal() + "': " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() { return "readFile(" + exp.toString() + ", " + varName + ")"; }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }
}
