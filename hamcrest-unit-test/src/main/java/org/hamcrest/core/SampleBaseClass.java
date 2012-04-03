package org.hamcrest.core;

public class SampleBaseClass {
    String value;

    public SampleBaseClass(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SampleBaseClass && value.equals(((SampleBaseClass) obj).value);
    }

    @Override
    public int hashCode() {
      return value.hashCode();
    }
}
