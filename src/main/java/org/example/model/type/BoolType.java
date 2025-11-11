package org.example.model.type;

import org.example.model.value.BoolValue;
import org.example.model.value.Value;

public class BoolType implements Type {
    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    public Value defaultValue() {
        return new BoolValue(false);
    }
}
