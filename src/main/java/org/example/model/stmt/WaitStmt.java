package org.example.model.stmt;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
import org.example.model.value.IntValue;
import org.example.model.adt.MyIDictionary;
import org.example.model.type.Type;

// wait(number): if number==0 do nothing; else push(print(number); wait(number-1))
public class WaitStmt implements IStmt {
    private final int number;

    public WaitStmt(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "wait(" + number + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        if (number == 0) {
            // do nothing (pop already performed by caller since execute is called after pop)
            return null;
        } else {
            // push a compound: print(number); wait(number-1)
            stk.push(new WaitStmt(number - 1));
            stk.push(new PrintStmt(new org.example.model.exp.ValueExp(new IntValue(number))));
            return null;
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStmt(number);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        // wait uses only integers, no variables, so it's fine
        return typeEnv;
    }
}
