package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.type.Type;
import org.example.model.value.Value;

public class ValueExp implements Exp {
    private final Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        return e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
