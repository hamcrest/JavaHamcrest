package org.hamcrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

/**
 * Utility class with static methods for accessing properties on JavaBean objects.
 * See <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/beans/index.html">https://docs.oracle.com/javase/8/docs/technotes/guides/beans/index.html</a> for
 * more information on JavaBeans.
 *
 * @author Iain McGinniss
 * @author Steve Freeman
 * @author Uno Kim
 * @since 1.1.0
 */
public class PropertyUtil {

    private PropertyUtil() {
    }

    /**
     * Returns the description of the property with the provided
     * name on the provided object's interface.
     *
     * @param propertyName
     *     the bean property name.
     * @param fromObj
     *     the object to check.
     * @return the descriptor of the property, or null if the property does not exist.
     * @throws IllegalArgumentException if there's an introspection failure
     */
    public static PropertyDescriptor getPropertyDescriptor(String propertyName, Object fromObj) throws IllegalArgumentException {
        for (PropertyDescriptor property : propertyDescriptorsFor(fromObj, null)) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }

        return null;
    }

    /**
     * Returns all the property descriptors for the class associated with the given object
     *
     * @param fromObj Use the class of this object
     * @param stopClass Don't include any properties from this ancestor class upwards.
     * @return Property descriptors
     * @throws IllegalArgumentException if there's an introspection failure
     */
    public static PropertyDescriptor[] propertyDescriptorsFor(Object fromObj, Class<Object> stopClass) throws IllegalArgumentException {
      try {
        return Introspector.getBeanInfo(fromObj.getClass(), stopClass).getPropertyDescriptors();
      } catch (IntrospectionException e) {
        throw new IllegalArgumentException("Could not get property descriptors for " + fromObj.getClass(), e);
      }
    }

    /**
     * Returns the description of the method with the provided
     * name on the provided object's interface.
     * This is what you need when you try to find a property from a target object
     * when it doesn't follow standard JavaBean specification, a Java Record for example.
     *
     * @param propertyName the object property name.
     * @param fromObj the object to check.
     * @param isNonVoid whether the method is non-void, which means the method has a return value.
     * @return the descriptor of the method, or null if the method does not exist.
     * @throws IllegalArgumentException if there's an introspection failure
     * @see <a href="https://docs.oracle.com/en/java/javase/17/language/records.html">Java Records</a>
     *
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

    /**
     * Empty object array, used for documenting that we are deliberately passing no arguments to a method.
     */
    public static final Object[] NO_ARGUMENTS = new Object[0];

}
