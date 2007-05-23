package org.hamcrest.core;

public class SampleBaseClass {
    String value;

    public SampleBaseClass(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public boolean equals(Object obj) {
        return obj instanceof SampleBaseClass && value.equals(((SampleBaseClass) obj).value);
    }
}
