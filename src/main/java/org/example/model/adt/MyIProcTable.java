package org.example.model.adt;

import org.example.exception.MyException;

import java.util.Map;

public interface MyIProcTable {
    void add(String name, Procedure proc) throws MyException;
    Procedure lookup(String name) throws MyException;
    boolean isDefined(String name);
    Map<String, Procedure> getContent();
    void setContent(Map<String, Procedure> content);
    String toFileString();
}
