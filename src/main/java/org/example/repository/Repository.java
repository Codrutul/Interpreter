package org.example.repository;

import org.example.exception.MyException;
import org.example.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> prgStateList;
    private final String logFilePath;

    public Repository(PrgState prgState, String logFilePath) {
        this.prgStateList = new ArrayList<>();
        this.prgStateList.add(prgState);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return prgStateList.get(0);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            PrgState programState = getCrtPrg();
            logFile.println(programState.toFileString());
            logFile.println("---------------------------------");
        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }
}
