package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.value.RefValue;
import org.example.model.value.Value;

public class ReadHeapExp implements Exp {
    private final Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        Value v = exp.eval(tbl, hp);
        if (!(v instanceof RefValue)) throw new MyException("readHeap argument is not a RefValue");
        int addr = ((RefValue) v).getAddr();
        if (!hp.isDefined(addr)) throw new MyException("address " + addr + " is not defined in heap");
        return hp.lookup(addr);
    }

    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() { return "rH(" + exp.toString() + ")"; }
}

