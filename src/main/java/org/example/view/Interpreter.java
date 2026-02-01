package org.example.view;

import org.example.controller.Controller;
import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyDictionary;
import org.example.model.adt.MyFileTable;
import org.example.model.adt.MyHeap;
import org.example.model.adt.MyList;
import org.example.model.adt.MyStack;
import org.example.model.exp.ArithExp;
import org.example.model.exp.VarExp;
import org.example.model.stmt.*;
import org.example.model.type.Type;
import org.example.model.type.IntType;
import org.example.repository.IRepository;
import org.example.repository.Repository;

import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the log file path:");
        String filename = scanner.nextLine();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));


        IStmt ex1 = ExampleCreator.getExample1();
        try {
            ex1.typecheck(new MyDictionary<>());
            // use shared proc table
            org.example.model.adt.MyProcTable pt1 = org.example.model.adt.MyProcTable.getShared();
            PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), pt1, ex1);
            IRepository repo1 = new Repository(prg1, filename);
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        } catch (MyException e) {
            System.out.println("Example 1 typecheck error: " + e.getMessage());
        }

        IStmt ex2 = ExampleCreator.getExample2();
        try {
            ex2.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable pt2 = org.example.model.adt.MyProcTable.getShared();
            PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), pt2, ex2);
            IRepository repo2 = new Repository(prg2, filename);
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        } catch (MyException e) {
            System.out.println("Example 2 typecheck error: " + e.getMessage());
        }

        IStmt ex3 = ExampleCreator.getExample3();
        try {
            ex3.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable pt3 = org.example.model.adt.MyProcTable.getShared();
            PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), pt3, ex3);
            IRepository repo3 = new Repository(prg3, filename);
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        } catch (MyException e) {
            System.out.println("Example 3 typecheck error: " + e.getMessage());
        }

        IStmt ex4 = ExampleCreator.getExample4();
        try {
            ex4.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable pt4 = org.example.model.adt.MyProcTable.getShared();
            PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), pt4, ex4);
            IRepository repo4 = new Repository(prg4, filename);
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        } catch (MyException e) {
            System.out.println("Example 4 typecheck error: " + e.getMessage());
        }

        IStmt ex5 = ExampleCreator.getExample5();
        try {
            ex5.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable pt5 = org.example.model.adt.MyProcTable.getShared();
            PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), pt5, ex5);
            IRepository repo5 = new Repository(prg5, filename);
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        } catch (MyException e) {
            System.out.println("Example 5 typecheck error: " + e.getMessage());
        }

        IStmt ex6 = ExampleCreator.getExample6();
        try {
            ex6.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable pt6 = org.example.model.adt.MyProcTable.getShared();
            PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), pt6, ex6);
            IRepository repo6 = new Repository(prg6, filename);
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        } catch (MyException e) {
            System.out.println("Example 6 typecheck error: " + e.getMessage());
        }

        IStmt ex7 = ExampleCreator.getExample7();
        try {
            ex7.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable pt7 = org.example.model.adt.MyProcTable.getShared();
            PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), pt7, ex7);
            IRepository repo7 = new Repository(prg7, filename);
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        } catch (MyException e) {
            System.out.println("Example 7 typecheck error: " + e.getMessage());
        }

        IStmt ex8 = ExampleCreator.getExample8();
        try {
            ex8.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable pt8 = org.example.model.adt.MyProcTable.getShared();
            PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), pt8, ex8);
            IRepository repo8 = new Repository(prg8, filename);
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        } catch (MyException e) {
            System.out.println("Example 8 typecheck error: " + e.getMessage());
        }

        // Problem: procedures example (hard-coded procedures into proc table)
        IStmt procEx = ExampleCreator.getProceduresExample();
        try {
            procEx.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable ptProc = org.example.model.adt.MyProcTable.getShared();
            // build procedure sum(a,b) body
            // procedure bodies declare local v first
            org.example.model.adt.Procedure sumProc = new org.example.model.adt.Procedure(java.util.Arrays.asList("a","b"),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new AssignStmt("v", new ArithExp(1, new VarExp("a"), new VarExp("b"))), new PrintStmt(new VarExp("v")))));
            org.example.model.adt.Procedure prodProc = new org.example.model.adt.Procedure(java.util.Arrays.asList("a","b"),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new AssignStmt("v", new ArithExp(3, new VarExp("a"), new VarExp("b"))), new PrintStmt(new VarExp("v")))));
            ptProc.add("sum", sumProc);
            ptProc.add("product", prodProc);
            PrgState prgProc = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ptProc, procEx);
            IRepository repoProc = new Repository(prgProc, filename);
            Controller ctrProc = new Controller(repoProc);
            menu.addCommand(new RunExample("9", procEx.toString(), ctrProc));
        } catch (MyException e) {
            System.out.println("Procedures example typecheck error: " + e.getMessage());
        }

        // Problem: sleep example
        IStmt sleepEx = ExampleCreator.getSleepExample();
        try {
            sleepEx.typecheck(new MyDictionary<>());
            org.example.model.adt.MyProcTable ptSleep = org.example.model.adt.MyProcTable.getShared();
            PrgState prgSleep = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ptSleep, sleepEx);
            IRepository repoSleep = new Repository(prgSleep, filename);
            Controller ctrSleep = new Controller(repoSleep);
            menu.addCommand(new RunExample("10", sleepEx.toString(), ctrSleep));
        } catch (MyException e) {
            System.out.println("Sleep example typecheck error: " + e.getMessage());
        }

        menu.show();
    }
}
