package org.example.view;

import org.example.model.exp.*;
import org.example.model.stmt.*;
import org.example.model.type.BoolType;
import org.example.model.type.IntType;
import org.example.model.type.RefType;
import org.example.model.type.StringType;
import org.example.model.value.BoolValue;
import org.example.model.value.IntValue;
import org.example.model.value.StringValue;

public class ExampleCreator {
    // Example 1: int v; v=2; Print(v)
    public static IStmt getExample1() {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
    }

    // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
    public static IStmt getExample2() {
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)), new ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

    }

    // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
    public static IStmt getExample3() {
        return new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

    }

    // Example 4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
    public static IStmt getExample4() {
        return new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));

    }

    // Example 5: int a; a = 10; int b; b = 12; print(a < b)
    public static IStmt getExample5() {
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(10))),
                        new CompStmt(new VarDeclStmt("b", new IntType()),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(12))),
                                        new PrintStmt(new RelationalExp("<", new VarExp("a"), new VarExp("b")))))));

    }

    // Example 6: while example: int v; v=4; while(v>0) print(v); v=v-1; print(v)
    public static IStmt getExample6() {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(
                                ">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
    }

    // Example 7: heap example
    //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
    public static IStmt getExample7() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewStmt("a", new VarExp("v")), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
    }

    // Example 8:
    // int v; Ref int a; v=10; new(a,22);
    // fork( wH(a,30); v=32; print(v); print(rH(a)) );
    // print(v); print(rH(a))
    public static IStmt getExample8() {
        IStmt declV = new VarDeclStmt("v", new IntType());
        IStmt declA = new VarDeclStmt("a", new RefType(new IntType()));
        IStmt assignV10 = new AssignStmt("v", new ValueExp(new IntValue(10)));
        IStmt newA22 = new NewStmt("a", new ValueExp(new IntValue(22)));

        // fork body: wH(a,30); v=32; print(v); print(rH(a))
        IStmt forkBody = new CompStmt(
                new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                        new CompStmt(
                                new PrintStmt(new VarExp("v")),
                                new PrintStmt(new ReadHeapExp(new VarExp("a")))
                        )
                )
        );
        IStmt forkStmt = new ForkStmt(forkBody);

        // after fork: print(v); print(rH(a))
        IStmt after = new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a"))));

        return new CompStmt(declV,
                new CompStmt(declA,
                        new CompStmt(assignV10,
                                new CompStmt(newA22,
                                        new CompStmt(forkStmt, after)))));
    }

}
