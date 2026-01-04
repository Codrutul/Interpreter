package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIFileTable;
import org.example.model.adt.MyIDictionary;
import org.example.model.exp.Exp;
import org.example.model.value.StringValue;
import org.example.model.value.Value;
import org.example.model.type.StringType;
import org.example.model.type.Type;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {
    private final Exp exp;

    public OpenRFile(Exp e) {
        exp = e;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
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
        if (fileTable.isDefined(fileName)) {
            throw new MyException("File already opened: " + fileName);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            fileTable.add(fileName, br);
        } catch (FileNotFoundException e) {
            throw new MyException("File not found: " + fileName);
        }
        return null;
    }


    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = exp.typecheck(typeEnv);
        if (t.equals(new StringType())) return typeEnv;
        else throw new MyException("OpenRFile: expression is not a string");
    }
}