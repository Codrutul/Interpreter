package org.example.model.adt;

import org.example.exception.MyException;
import org.example.model.stmt.IStmt;

import java.util.HashMap;
import java.util.Map;

public class MyProcTable implements MyIProcTable {
    // use a shared static map so different instances (e.g. GUI vs controller-created) see the same procedures
    private static final HashMap<String, Procedure> table = new HashMap<>();
    private static final MyProcTable shared = new MyProcTable();

    public static MyProcTable getShared() { return shared; }

    public MyProcTable() { /* no-op */ }

    @Override
    public void add(String name, Procedure proc) throws MyException {
        if (table.containsKey(name)) throw new MyException("Procedure already defined: " + name);
        table.put(name, proc);
    }

    @Override
    public Procedure lookup(String name) throws MyException {
        if (!table.containsKey(name)) throw new MyException("Procedure not defined: " + name);
        return table.get(name);
    }

    @Override
    public boolean isDefined(String name) { return table.containsKey(name); }

    @Override
    public Map<String, Procedure> getContent() { return table; }

    @Override
    public void setContent(Map<String, Procedure> content) { table.clear(); table.putAll(content); }

    @Override
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Procedure> e : table.entrySet()) {
            sb.append(e.getKey()).append(" -> ").append(e.getValue().toString()).append("\n");
        }
        return sb.toString();
    }
}
