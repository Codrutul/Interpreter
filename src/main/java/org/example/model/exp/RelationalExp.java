package org.example.model.exp;

import org.example.exception.MyException;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.type.BoolType;
import org.example.model.type.IntType;
import org.example.model.type.Type;
import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class RelationalExp implements Exp {
    private final String op;
    private final Exp exp1, exp2;

    public RelationalExp(String op, Exp exp1, Exp exp2) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws MyException {
        Value v1 = exp1.eval(tbl, hp);
        Value v2 = exp2.eval(tbl, hp);
        if (!(v1 instanceof IntValue) || !(v2 instanceof IntValue)) {
            throw new MyException("Relational: both operands must be integers");
        }
        int n1 = ((IntValue) v1).getVal();
        int n2 = ((IntValue) v2).getVal();
        switch (op) {
            case "<": return new IntValue(n1 < n2 ? 1 : 0);
            case "<=": return new IntValue(n1 <= n2 ? 1 : 0);
            case ">": return new IntValue(n1 > n2 ? 1 : 0);
            case ">=": return new IntValue(n1 >= n2 ? 1 : 0);
            case "==": return new IntValue(n1 == n2 ? 1 : 0);
            case "!=": return new IntValue(n1 != n2 ? 1 : 0);
            default: throw new MyException("Unknown relational operator " + op);
        }
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + op + " " + exp2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(op, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = exp1.typecheck(typeEnv);
        Type t2 = exp2.typecheck(typeEnv);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType())) {
                return new BoolType();
            } else throw new MyException("second operand is not an integer");
        } else throw new MyException("first operand is not an integer");
    }
}
