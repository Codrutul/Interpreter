package org.example.controller;

import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.MyIHeap;
import org.example.model.stmt.IStmt;
import org.example.model.value.RefValue;
import org.example.model.value.Value;
import org.example.repository.IRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
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

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        // log before
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
            }
        });

        // prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    try {
                        return p.oneStep();
                    } catch (MyException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = new ArrayList<>();
        try {
            List<Future<PrgState>> futures = executor.invokeAll(callList);
            for (Future<PrgState> f : futures) {
                try {
                    PrgState np = f.get();
                    if (np != null) newPrgList.add(np);
                } catch (ExecutionException e) {
                    // unwrap and rethrow as MyException
                    Throwable cause = e.getCause();
                    if (cause instanceof MyException) throw (MyException) cause;
                    else throw new MyException("Error during parallel execution: " + cause.getMessage());
                }
            }
        } catch (InterruptedException e) {
            throw new MyException("Execution interrupted: " + e.getMessage());
        }

        prgList.addAll(newPrgList);

        // log after
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
            }
        });

        // update repository
        repo.setPrgList(prgList);
    }

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        try {
            List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
            while (prgList.size() > 0) {
                oneStepForAllPrg(prgList);

                // garbage collect: gather all symbol table values from all programs
                List<Value> allSymVals = repo.getPrgList().stream()
                        .flatMap(p -> p.getSymTable().getContent().values().stream())
                        .collect(Collectors.toList());

                // heap is shared; take any program's heap (if exists)
                if (!repo.getPrgList().isEmpty()) {
                    MyIHeap<Integer, Value> heap = repo.getPrgList().get(0).getHeap();
                    heap.setContent(safeGarbageCollector(allSymVals, heap.getContent()));
                }

                prgList = removeCompletedPrg(repo.getPrgList());
            }
        } finally {
            executor.shutdownNow();
            // ensure repository updated
            repo.setPrgList(removeCompletedPrg(repo.getPrgList()));
        }
    }

    public IRepository getRepo() {
        return repo;
    }
}
