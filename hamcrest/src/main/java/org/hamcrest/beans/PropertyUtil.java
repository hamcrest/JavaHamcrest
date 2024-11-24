package org.hamcrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
     * Returns the description of the read accessor method with the provided
     * name on the provided object's interface.
     * This is what you need when you try to find a property from a target object
     * when it doesn't follow standard JavaBean specification, a Java Record for example.
     *
     * @param propertyName the object property name.
     * @param fromObj the object to check.
     * @return the descriptor of the method, or null if the method does not exist.
     * @throws IllegalArgumentException if there's an introspection failure
     * @see <a href="https://docs.oracle.com/en/java/javase/17/language/records.html">Java Records</a>
     *
     */
    public static MethodDescriptor getMethodDescriptor(String propertyName, Object fromObj) throws IllegalArgumentException {
        for (MethodDescriptor method : recordReadAccessorMethodDescriptorsFor(fromObj, null)) {
            if (method.getName().equals(propertyName)) {
                return method;
            }
        }

        return null;
    }

    /**
     * Returns read accessor method descriptors for the class associated with the given object.
     * This is useful when you find getter methods for the fields from the object
     * when it doesn't follow standard JavaBean specification, a Java Record for example.
     * Be careful as this doesn't return standard JavaBean getter methods, like a method starting with {@code get-}.
     *
     * @param fromObj Use the class of this object
     * @param stopClass Don't include any properties from this ancestor class upwards.
     * @return Method descriptors for read accessor methods
     * @throws IllegalArgumentException if there's an introspection failure
     */
    public static MethodDescriptor[] recordReadAccessorMethodDescriptorsFor(Object fromObj, Class<Object> stopClass) throws IllegalArgumentException {
        try {
            Set<String> recordComponentNames = getFieldNames(fromObj);
            MethodDescriptor[] methodDescriptors = Introspector.getBeanInfo(fromObj.getClass(), stopClass).getMethodDescriptors();

            return Arrays.stream(methodDescriptors)
                    .filter(x -> recordComponentNames.contains(x.getDisplayName()))
                    .filter(x -> x.getMethod().getReturnType() != void.class)
                    .filter(x -> x.getMethod().getParameterCount() == 0)
                    .toArray(MethodDescriptor[]::new);
        } catch (IntrospectionException e) {
            throw new IllegalArgumentException("Could not get method descriptors for " + fromObj.getClass(), e);
        }
    }

    /**
     * Returns the field names of the given object.
     * It can be the names of the record components of Java Records, for example.
     *
     * @param fromObj the object to check
     * @return The field names
     * @throws IllegalArgumentException if there's a security issue reading the fields
     */
    public static Set<String> getFieldNames(Object fromObj) throws IllegalArgumentException {
        try {
            return Arrays.stream(fromObj.getClass().getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toSet());
        } catch (SecurityException e) {
            throw new IllegalArgumentException("Could not get record component names for " + fromObj.getClass(), e);
        }
    }

    /**
     * Empty object array, used for documenting that we are deliberately passing no arguments to a method.
     */
    public static final Object[] NO_ARGUMENTS = new Object[0];

}
