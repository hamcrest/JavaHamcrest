package org.hamcrest.internal;

import java.lang.reflect.Array;

/**
 * Delegator to java.lang.reflect.Array, since GWT does not provide that.
 */
public class ArrayAccess {
    private ArrayAccess() {
    }
    
    public static int getLength(Object array) {
        return Array.getLength(array);
    }
    
    public static Object get(Object array, int idx) {
        return Array.get(array, idx);
    }
    
}
