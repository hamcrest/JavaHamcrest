package org.hamcrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;

/**
 * Utility class with static methods for accessing methods on regular Java objects.
 * This utility class is mainly used to get property information from Java Records
 * as they don't conform to the JavaBeans conventions.
 * This might not be necessary in the next major release of Hamcrest which includes JDK upgrade to 17.
 *
 * @see PropertyUtil
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/beans/index.html">More information on JavaBeans</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/language/records.html">Java Records</a>
 * @author Uno Kim
 * @since 1.3.2
 */
public class MethodUtil {

    private MethodUtil() {}

    /**
     * Returns the description of the method with the provided
     * name on the provided object's interface.
     *
     * @param propertyName the object property name.
     * @param fromObj the object to check.
     * @param isNonVoid whether the method is non-void, which means the method has a return value.
     * @return the descriptor of the method, or null if the method does not exist.
     * @throws IllegalArgumentException if there's an introspection failure
     */
    public static MethodDescriptor getMethodDescriptor(String propertyName, Object fromObj, boolean isNonVoid) throws IllegalArgumentException {
        for (MethodDescriptor method : methodDescriptorsFor(fromObj, null)) {
            if (method.getName().equals(propertyName) && (!isNonVoid || method.getMethod().getReturnType() != void.class)) {
                return method;
            }
        }

        return null;
    }

    /**
     * Returns all the method descriptors for the class associated with the given object
     *
     * @param fromObj Use the class of this object
     * @param stopClass Don't include any properties from this ancestor class upwards.
     * @return Method descriptors
     * @throws IllegalArgumentException if there's an introspection failure
     */
    public static MethodDescriptor[] methodDescriptorsFor(Object fromObj, Class<Object> stopClass) throws IllegalArgumentException {
      try {
        return Introspector.getBeanInfo(fromObj.getClass(), stopClass).getMethodDescriptors();
      } catch (IntrospectionException e) {
        throw new IllegalArgumentException("Could not get method descriptors for " + fromObj.getClass(), e);
      }
    }

}
