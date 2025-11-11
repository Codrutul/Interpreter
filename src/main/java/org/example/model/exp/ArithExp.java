package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.type.IntType;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class ArithExp implements Exp {
    private final Exp e1;
    private final Exp e2;
    private final int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(int op, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new IntValue(n1 + n2);
                if (op == 2) return new IntValue(n1 - n2);
                if (op == 3) return new IntValue(n1 * n2);
                if (op == 4) {
                    if (n2 == 0) throw new MyException("division by zero");
                    else return new IntValue(n1 / n2);
                }
            } else {
                throw new MyException("second operand is not an integer");
            }
        } else {
            throw new MyException("first operand is not an integer");
        }
        return null;
    }

    @Override
    public String toString() {
        String opString = "";
        if (op == 1) opString = "+";
        if (op == 2) opString = "-";
        if (op == 3) opString = "*";
        if (op == 4) opString = "/";
        return e1.toString() + " " + opString + " " + e2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }
}
