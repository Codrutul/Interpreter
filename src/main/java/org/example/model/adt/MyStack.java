package org.example.model.adt;

import org.example.exception.MyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private final Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() throws MyException {
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> getReversed() {
        List<T> l = Arrays.asList((T[]) stack.toArray());
        Collections.reverse(l);
        return l;
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
