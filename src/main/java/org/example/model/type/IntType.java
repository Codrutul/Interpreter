package org.example.model.type;

import org.example.model.value.IntValue;
import org.example.model.value.Value;

public class IntType implements Type {
    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    public Value defaultValue() {
        return new IntValue(0);
    }
}
