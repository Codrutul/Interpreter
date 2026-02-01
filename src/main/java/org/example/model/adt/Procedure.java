package org.example.model.adt;

import org.example.model.stmt.IStmt;

import java.util.List;

public class Procedure {
    private final List<String> params;
    private final IStmt body;

    public Procedure(List<String> params, IStmt body) {
        this.params = params;
        this.body = body;
    }

    public List<String> getParams() { return params; }
    public IStmt getBody() { return body; }

    @Override
    public String toString() {
        return "(" + String.join(",", params) + ") -> " + body.toString();
    }
}

