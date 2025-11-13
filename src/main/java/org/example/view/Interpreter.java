package org.example.view;

import org.example.controller.Controller;
import org.example.model.PrgState;
import org.example.model.adt.MyDictionary;
import org.example.model.adt.MyFileTable;
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

        // Example 1: int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), ex1);
        IRepository repo1 = new Repository(prg1, filename);
        Controller ctr1 = new Controller(repo1);
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));

        // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)), new ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), ex2);
        IRepository repo2 = new Repository(prg2, filename);
        Controller ctr2 = new Controller(repo2);
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));

        // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), ex3);
        IRepository repo3 = new Repository(prg3, filename);
        Controller ctr3 = new Controller(repo3);
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));

        // Example 4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));
        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), ex4);
        IRepository repo4 = new Repository(prg4, filename);
        Controller ctr4 = new Controller(repo4);
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));

        // Example 5: int a; a = 10; int b; b = 12; print(a < b)
        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(10))),
                        new CompStmt(new VarDeclStmt("b", new IntType()),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(12))),
                                        new PrintStmt(new RelationalExp("<", new VarExp("a"), new VarExp("b")))))));
        PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyFileTable<>(), ex5);
        IRepository repo5 = new Repository(prg5, filename);
        Controller ctr5 = new Controller(repo5);
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));


        menu.show();
    }
}
