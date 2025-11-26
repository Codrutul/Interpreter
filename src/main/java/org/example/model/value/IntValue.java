package org.example.model.value;

import org.example.model.type.IntType;
import org.example.model.type.Type;

public class IntValue implements Value {
    private final int val;

    public IntValue(int v) {
        this.val = v;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof IntValue)
            return ((IntValue) another).getVal() == val;
        return false;
    }
}