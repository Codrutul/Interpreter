package org.example.model.value;

import org.example.model.type.Type;

public interface Value {
    Type getType();
    Value deepCopy();
}
