package org.example.view;

import org.example.controller.Controller;
import org.example.exception.MyException;
import org.example.model.value.Value;

public class RunExample extends Command {
    private final Controller ctr;
    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            ctr.allStep();
            // after execution, print the output list to console
            try {
                var prg = ctr.getRepo().getCrtPrg();
                var out = prg.getOut();
                System.out.println("Program output:");
                for (Value v : out.getList()) {
                    System.out.println(v.toString());
                }
            } catch (Exception e) {
                // if anything goes wrong printing the output, show message
                System.out.println("(could not print program output): " + e.getMessage());
            }
        } catch (MyException e) {
            System.out.println("Execution Error: " + e.getMessage());
        }
    }
}