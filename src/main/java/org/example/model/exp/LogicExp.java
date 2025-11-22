package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.value.BoolValue;
import org.example.model.value.Value;

public class LogicExp implements Exp {
    private final Exp e1, e2;
    private final int op; //1-and,2-or

    public LogicExp(int op, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1 instanceof BoolValue) {
            v2 = e2.eval(tbl, hp);
            if (v2 instanceof BoolValue) {
                boolean b1 = ((BoolValue) v1).getVal();
                boolean b2 = ((BoolValue) v2).getVal();
                if (op == 1) return new BoolValue(b1 && b2);
                if (op == 2) return new BoolValue(b1 || b2);
            } else {
                throw new MyException("second operand is not a boolean");
            }
        } else {
            throw new MyException("first operand is not a boolean");
        }
        return null;
    }

    @Override
    public String toString() {
        String opString = "";
        if (op == 1) opString = "and";
        if (op == 2) opString = "or";
        return e1.toString() + " " + opString + " " + e2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(op, e1.deepCopy(), e2.deepCopy());
    }
}
