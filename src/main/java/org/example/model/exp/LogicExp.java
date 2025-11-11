package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.type.BoolType;
import org.example.model.value.BoolValue;
import org.example.model.value.Value;

public class LogicExp implements Exp {
    private final Exp e1;
    private final Exp e2;
    private final int op; //1-and, 2-or

    public LogicExp(int op, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = b1.getVal();
                n2 = b2.getVal();
                if (op == 1) return new BoolValue(n1 && n2);
                if (op == 2) return new BoolValue(n1 || n2);
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
