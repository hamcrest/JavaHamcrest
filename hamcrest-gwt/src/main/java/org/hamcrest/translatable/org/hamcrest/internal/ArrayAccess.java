package org.hamcrest.internal;

/**
 * GWT does not provide the Array class, but because it might eventually do
 * provide it, we are not translating java.lang.reflect.Array, but this
 * delegator class.
 *
 * @author Pascal Muetschard (pmuetschard@google.com)
 */
public class ArrayAccess {
    private ArrayAccess() {
    }
    
    public static int getLength(Object array) {
        if (array instanceof byte[]) {
            return ((byte[])array).length;
        } else if (array instanceof short[]) {
            return ((short[])array).length;
        } else if (array instanceof char[]) {
            return ((char[])array).length;
        } else if (array instanceof int[]) {
            return ((int[])array).length;
        } else if (array instanceof long[]) {
            return ((long[])array).length;
        } else if (array instanceof float[]) {
            return ((float[])array).length;
        } else if (array instanceof double[]) {
            return ((double[])array).length;
        } else if (array instanceof boolean[]) {
            return ((boolean[])array).length;
        } else {
            return ((Object[])array).length;
        }
    }
    
    public static Object get(Object array, int idx) {
        if (array instanceof byte[]) {
            return new Byte(((byte[])array)[idx]);
        } else if (array instanceof short[]) {
            return new Short(((short[])array)[idx]);
        } else if (array instanceof char[]) {
            return new Character(((char[])array)[idx]);
        } else if (array instanceof int[]) {
            return new Integer(((int[])array)[idx]);
        } else if (array instanceof long[]) {
            return new Long(((long[])array)[idx]);
        } else if (array instanceof float[]) {
            return new Float(((float[])array)[idx]);
        } else if (array instanceof double[]) {
            return new Double(((double[])array)[idx]);
        } else if (array instanceof boolean[]) {
            return new Boolean(((boolean[])array)[idx]);
        } else {
            return ((Object[])array)[idx];
        }
    }
}
