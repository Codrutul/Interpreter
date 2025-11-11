package org.example.controller;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
import org.example.model.stmt.IStmt;
import org.example.repository.IRepository;

public class Controller {
    private final IRepository repo;
    private boolean displayFlag = false;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        if (stk.isEmpty()) {
            throw new MyException("prgstate stack is empty");
        }
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public void allStep() {
        PrgState prg = repo.getCrtPrg();
        if (displayFlag) {
            System.out.println(prg.toString());
        }
        try {
            while (!prg.getStk().isEmpty()) {
                oneStep(prg);
                if (displayFlag) {
                    System.out.println(prg.toString());
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
