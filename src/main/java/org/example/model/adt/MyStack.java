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

    @Override
    public String toFileString() {
        StringBuilder result = new StringBuilder();
        for (T elem : stack) {
            result.append(elem.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public MyIStack<T> deepCopy() throws MyException {
        MyIStack<T> newStack = new MyStack<>();
        for (T elem : stack) {
            newStack.push(elem);
        }
        return newStack;
    }
}
