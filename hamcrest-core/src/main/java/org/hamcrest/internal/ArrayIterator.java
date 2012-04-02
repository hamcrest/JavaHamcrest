package org.hamcrest.internal;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayIterator implements Iterator<Object> {
    private final Object array;
    private int currentIndex = 0;
    
    public ArrayIterator(Object array) {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("not an array");
        }
        this.array = array;
    }
    
    @Override
    public boolean hasNext() {
        return currentIndex < Array.getLength(array);
    }

    @Override
    public Object next() {
        return Array.get(array, currentIndex++);
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("cannot remove items from an array");
    }
}
