package org.example.model.value;

import org.example.model.type.StringType;
import org.example.model.type.Type;

public class StringValue implements Value {
    private final String val;

    public StringValue(String v) {
        this.val = v;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "\"" + val + "\"";
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof StringValue)
            return ((StringValue) another).getVal().equals(val);
        return false;
    }
}