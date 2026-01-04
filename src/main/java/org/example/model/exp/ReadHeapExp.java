package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.type.RefType;
import org.example.model.type.Type;
import org.example.model.value.RefValue;
import org.example.model.value.Value;

import java.util.Optional;

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
        Optional<Value> valueOptional = hp. lookup(addr);
        if (valueOptional.isPresent())
            return valueOptional.get();
        else
            throw new MyException("address " + addr + "is not in the heap");

    }

    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else throw new MyException("the rH argument is not a Ref Type");
    }
}
