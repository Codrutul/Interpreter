package org.example.controller;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIStack;
import org.example.model.adt.MyIHeap;
import org.example.model.stmt.IStmt;
import org.example.model.value.RefValue;
import org.example.model.value.Value;
import org.example.repository.IRepository;

import java.util.*;
import java.util.stream.Collectors;

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

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddr())
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // Better garbage collector: keep addresses reachable from symtable and from heap refs recursively
    private Map<Integer, Value> safeGarbageCollector(Collection<Value> symTableValues, Map<Integer, Value> heap) {
        Set<Integer> reachable = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        // start with addresses from symtable
        for (Value v : symTableValues) {
            if (v instanceof RefValue) {
                int addr = ((RefValue) v).getAddr();
                if (heap.containsKey(addr) && reachable.add(addr)) stack.push(addr);
            }
        }
        // traverse heap to find nested refs
        while (!stack.isEmpty()) {
            int addr = stack.pop();
            Value val = heap.get(addr);
            if (val instanceof RefValue) {
                int innerAddr = ((RefValue) val).getAddr();
                if (heap.containsKey(innerAddr) && reachable.add(innerAddr)) stack.push(innerAddr);
            }
        }
        // filter heap by reachable addresses
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        repo.logPrgStateExec();
        while (!prg.getStk().isEmpty()) {
            oneStep(prg);
            repo.logPrgStateExec();
            MyIHeap<Integer, Value> heap = prg.getHeap();
            Map<Integer, Value> newContent = safeGarbageCollector(prg.getSymTable().getContent().values(), heap.getContent());
            heap.setContent(newContent);
            repo.logPrgStateExec();
        }
    }

    public IRepository getRepo() {
        return repo;
    }
}
