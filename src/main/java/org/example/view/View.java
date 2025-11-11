package org.example.view;

import org.example.controller.Controller;
import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyDictionary;
import org.example.model.adt.MyList;
import org.example.model.adt.MyStack;
import org.example.model.exp.ArithExp;
import org.example.model.exp.ValueExp;
import org.example.model.exp.VarExp;
import org.example.model.stmt.*;
import org.example.model.type.BoolType;
import org.example.model.type.IntType;
import org.example.model.value.BoolValue;
import org.example.model.value.IntValue;
import org.example.repository.Repository;

import java.util.Scanner;

public class View {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Run example 1");
            System.out.println("2. Run example 2");
            System.out.println("3. Run example 3");
            System.out.print("Your option: ");
            String option = scanner.nextLine();

            if (option.equals("0")) {
                break;
            } else if (option.equals("1")) {
                runExample1();
            } else if (option.equals("2")) {
                runExample2();
            } else if (option.equals("3")) {
                runExample3();
            } else {
                System.out.println("Invalid option");
            }
        }
    }

    private void runExample1() {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        runStatement(ex1);
    }

    private void runExample2() {
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        runStatement(ex2);
    }

    private void runExample3() {
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        runStatement(ex3);
    }

    private void runStatement(IStmt stmt) {
        try {
            PrgState prg = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), stmt);
            Repository repo = new Repository(prg);
            Controller ctrl = new Controller(repo);
            ctrl.allStep();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
