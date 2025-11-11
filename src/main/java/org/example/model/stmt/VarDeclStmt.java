package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.type.BoolType;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.BoolValue;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class VarDeclStmt implements IStmt {
    private final String name;
    private final Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isDefined(name)) {
            throw new MyException("variable is already declared");
        } else {
            if (typ.equals(new IntType())) {
                symTbl.add(name, new IntValue(0));
            } else {
                symTbl.add(name, new BoolValue(false));
            }
        }
        return state;
    }

    @Override
    public String toString() {
        return typ.toString() + " " + name;
    }
}
