package org.hamcrest.internal;

import org.hamcrest.Description;

public class StringDescription implements Description {

    private final StringBuffer buffer = new StringBuffer();

    public Description append(String text) {
        buffer.append(text);
        return this;
    }

    public Description append(Object object) {
        buffer.append(object);
        return this;
    }

    public String toString() {
        return buffer.toString();
    }
}
