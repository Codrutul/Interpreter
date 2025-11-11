package org.example.model.value;

import org.example.model.type.BoolType;
import org.example.model.type.Type;

public class BoolValue implements Value {
    private final boolean val;

    public BoolValue(boolean v) {
        this.val = v;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolValue)
            return ((BoolValue) another).getVal() == val;
        return false;
    }
}