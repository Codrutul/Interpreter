package org.example.view;

import org.example.controller.Controller;
import org.example.model.PrgState;
import org.example.model.adt.MyDictionary;
import org.example.model.adt.MyFileTable;
import org.example.model.adt.MyHeap;
import org.example.model.adt.MyIHeap;
import org.example.model.adt.MyList;
import org.example.model.adt.MyStack;
import org.example.model.exp.*;
import org.example.model.stmt.*;
import org.example.model.type.BoolType;
import org.example.model.type.IntType;
import org.example.model.type.StringType;
import org.example.model.value.BoolValue;
import org.example.model.value.IntValue;
import org.example.model.value.StringValue;
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
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ex1);
        IRepository repo1 = new Repository(prg1, filename);
        Controller ctr1 = new Controller(repo1);
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));

        IStmt ex2 = ExampleCreator.getExample2();
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ex2);
        IRepository repo2 = new Repository(prg2, filename);
        Controller ctr2 = new Controller(repo2);
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));

        IStmt ex3 = ExampleCreator.getExample3();
        PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ex3);
        IRepository repo3 = new Repository(prg3, filename);
        Controller ctr3 = new Controller(repo3);
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));

        IStmt ex4 = ExampleCreator.getExample4();
        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ex4);
        IRepository repo4 = new Repository(prg4, filename);
        Controller ctr4 = new Controller(repo4);
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));

        IStmt ex5 = ExampleCreator.getExample5();
        PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ex5);
        IRepository repo5 = new Repository(prg5, filename);
        Controller ctr5 = new Controller(repo5);
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));

        IStmt ex6 = ExampleCreator.getExample6();
        PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ex6);
        IRepository repo6 = new Repository(prg6, filename);
        Controller ctr6 = new Controller(repo6);
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));

        IStmt ex7 = ExampleCreator.getExample7();
        PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), new MyHeap(), ex7);
        IRepository repo7 = new Repository(prg7, filename);
        Controller ctr7 = new Controller(repo7);
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));


        menu.show();
    }
}
