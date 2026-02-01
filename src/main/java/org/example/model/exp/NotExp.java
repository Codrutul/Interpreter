package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.type.BoolType;
import org.example.model.type.Type;
import org.example.model.value.BoolValue;
import org.example.model.value.Value;

public class NotExp implements Exp {
    private Exp exp;

    public NotExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        Value val = exp.eval(tbl, hp);
        if (!val.getType().equals(new BoolType()))
            throw new MyException("Expression is not boolean!");
        return new BoolValue(!((BoolValue)val).getVal());
    }

    @Override
    public Exp deepCopy() {
        return new NotExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "!(" + exp.toString() + ")";
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return exp.typecheck(typeEnv);
    }
}
