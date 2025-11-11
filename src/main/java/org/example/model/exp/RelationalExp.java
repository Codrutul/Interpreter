package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.type.IntType;
import org.example.model.value.BoolValue;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class RelationalExp implements Exp {
    private final Exp exp1;
    private final Exp exp2;
    private final String op; // "<", "<=", "==", "!=", ">", ">="

    public RelationalExp(String op, Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1 = exp1.eval(tbl);
        if (!v1.getType().equals(new IntType())) {
            throw new MyException("First operand is not an integer.");
        }
        Value v2 = exp2.eval(tbl);
        if (!v2.getType().equals(new IntType())) {
            throw new MyException("Second operand is not an integer.");
        }

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1 = i1.getVal();
        int n2 = i2.getVal();

        return switch (op) {
            case "<" -> new BoolValue(n1 < n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">" -> new BoolValue(n1 > n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new MyException("Invalid relational operator: " + op);
        };
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + op + " " + exp2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(op, exp1.deepCopy(), exp2.deepCopy());
    }
}
