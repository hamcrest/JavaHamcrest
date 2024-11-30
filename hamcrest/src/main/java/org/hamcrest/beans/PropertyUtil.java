package org.hamcrest.beans;

import java.beans.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
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

    public static <T> FeatureDescriptor[] featureDescriptorsFor(T expectedBean) {
        FeatureDescriptor[] descriptors = propertyDescriptorsFor(expectedBean, Object.class);
        if (descriptors != null && descriptors.length > 0) {
            return descriptors;
        }
        return recordReadAccessorMethodDescriptorsFor(expectedBean);
    }

    public static <T> FeatureDescriptor getFeatureDescriptor(String propertyName, T bean) {
        FeatureDescriptor property = getPropertyDescriptor(propertyName, bean);
        if (property != null) {
            return property;
        }
        return getMethodDescriptor(propertyName, bean);
    }

    /**
     * Returns the description of the property with the provided
     * name on the provided object's interface.
     *
     * @param propertyName the bean property name.
     * @param fromObj the object to check.
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
        for (MethodDescriptor method : recordReadAccessorMethodDescriptorsFor(fromObj)) {
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
     * @return Method descriptors for read accessor methods
     * @throws IllegalArgumentException if there's an introspection failure
     */
    public static MethodDescriptor[] recordReadAccessorMethodDescriptorsFor(Object fromObj) throws IllegalArgumentException {
        try {
            Set<String> fieldNames = getFieldNames(fromObj);
            MethodDescriptor[] methodDescriptors = Introspector.getBeanInfo(fromObj.getClass(), null).getMethodDescriptors();

            return Arrays.stream(methodDescriptors)
                    .filter(IsFieldAccessor.forOneOf(fieldNames))
                    .toArray(MethodDescriptor[]::new);
        } catch (IntrospectionException e) {
            throw new IllegalArgumentException("Could not get method descriptors for " + fromObj.getClass(), e);
        }
    }

    /**
     * Predicate that checks if a given {@link MethodDescriptor} corresponds to a field.
     * <p>
     * This predicate assumes a method is a field access if the method name exactly
     * matches the field name, takes no parameters and returns a non-void type.
     */
    private static class IsFieldAccessor implements Predicate<MethodDescriptor> {
        private final Set<String> fieldNames;

        private IsFieldAccessor(Set<String> fieldNames) {
            this.fieldNames = fieldNames;
        }

        public static IsFieldAccessor forOneOf(Set<String> fieldNames) {
            return new IsFieldAccessor(fieldNames);
        }

        @Override
        public boolean test(MethodDescriptor md) {
            return fieldNames.contains(md.getDisplayName()) &&
                    md.getMethod().getReturnType() != void.class &&
                    md.getMethod().getParameterCount() == 0;
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
