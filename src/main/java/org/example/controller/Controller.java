package org.example.controller;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
import org.example.model.stmt.IStmt;
import org.example.repository.IRepository;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
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
        PrgState prg = repo.getCrtPrg(); // repo is the controller field of type MyRepoInterface
        System.out.println(prg);
        while (!prg.getStk().isEmpty()) {
            oneStep(prg);
            System.out.println(prg);
        }
    }
}
