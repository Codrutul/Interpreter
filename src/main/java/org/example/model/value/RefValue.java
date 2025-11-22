package org.example.model.value;

import org.example.model.type.RefType;
import org.example.model.type.Type;

public class RefValue implements Value {
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefValue) {
            RefValue rv = (RefValue) another;
            return rv.address == address && rv.locationType.equals(locationType);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + address + "," + locationType.toString() + ")";
    }
}

