package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.type.BoolType;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.BoolValue;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class EqualExp implements Exp {
    private final Exp e1, e2;

    public EqualExp(Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        Value v1 = e1.eval(tbl, hp);
        Value v2 = e2.eval(tbl, hp);
        if (v1 instanceof IntValue && v2 instanceof IntValue) {
            int n1 = ((IntValue) v1).getVal();
            int n2 = ((IntValue) v2).getVal();
            return new BoolValue(n1 == n2);
        }
        throw new MyException("Equality: both operands must be integers");
    }

    @Override
    public String toString() {
        return e1.toString() + " == " + e2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new EqualExp(e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = e1.typecheck(typeEnv);
        Type t2 = e2.typecheck(typeEnv);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType())) {
                return new BoolType();
            } else throw new MyException("second operand is not an integer");
        } else throw new MyException("first operand is not an integer");
    }
}

