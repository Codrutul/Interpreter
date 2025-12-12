package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIDictionary;
import org.example.model.adt.MyIHeap;
import org.example.model.exp.Exp;
import org.example.model.value.RefValue;
import org.example.model.value.Value;
import org.example.model.type.RefType;

public class WriteHeapStmt implements IStmt {
    private final String varName;
    private final Exp expr;

    public WriteHeapStmt(String varName, Exp expr) {
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "wH(" + varName + "," + expr.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if (!symTable.isDefined(varName)) throw new MyException("Variable " + varName + " not defined");
        Value varVal = symTable.lookup(varName);
        if (!(varVal instanceof RefValue)) throw new MyException("Variable " + varName + " is not RefValue");
        int addr = ((RefValue) varVal).getAddr();
        if (!heap.isDefined(addr)) throw new MyException("address " + addr + " is not in the heap");
        Value evalVal = expr.eval(symTable, heap);
        RefType refType = (RefType) varVal.getType();
        if (!evalVal.getType().equals(refType.getInner()))
            throw new MyException("Type of expression and locationType do not match");
        heap.update(addr, evalVal);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(varName, expr.deepCopy());
    }
}
