package org.hamcrest.beans;

import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class with static methods for accessing properties on JavaBean objects, or bean-like
 * objects such as records.
 * <p>
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

    public static <T> List<PropertyAccessor> propertyAccessorsFor(T bean) {
        PropertyDescriptor[] descriptors = propertyDescriptorsFor(bean, Object.class);
        if (descriptors != null && descriptors.length > 0) {
            return Arrays.stream(descriptors)
                    .map(pd -> PropertyAccessor.fromProperty(bean, pd))
                    .collect(Collectors.toList());
        }

        MethodDescriptor[] methodDescriptors = fieldReadDescriptorsFor(bean);
        return Arrays.stream(methodDescriptors)
                .map(md -> PropertyAccessor.fromMethod(bean, md))
                .collect(Collectors.toList());
    }

    public static <T> PropertyAccessor getPropertyAccessor(String propertyName, T bean) {
        return propertyAccessorsFor(bean).stream()
                .filter(pa -> propertyName.equals(pa.propertyName()))
                .findFirst()
                .orElse(null);
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
     * Returns read accessor method descriptors for the class associated with the given object.
     * This is useful when you find getter methods for the fields from the object
     * when it doesn't follow standard JavaBean specification, a Java Record for example.
     * Be careful as this doesn't return standard JavaBean getter methods, like a method starting with {@code get-}.
     *
     * @param fromObj Use the class of this object
     * @return Method descriptors for read accessor methods
     * @throws IllegalArgumentException if there's an introspection failure
     */
    public static MethodDescriptor[] fieldReadDescriptorsFor(Object fromObj) throws IllegalArgumentException {
        try {
            Set<String> fieldNames = getFieldNames(fromObj);
            MethodDescriptor[] methodDescriptors = Introspector.getBeanInfo(fromObj.getClass(), null).getMethodDescriptors();

            return Arrays.stream(methodDescriptors)
                    .filter(IsPropertyAccessor.forOneOf(fieldNames))
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
    private static class IsPropertyAccessor implements Predicate<MethodDescriptor> {
        private final Set<String> propertyNames;

        private IsPropertyAccessor(Set<String> propertyNames) {
            this.propertyNames = propertyNames;
        }

        public static IsPropertyAccessor forOneOf(Set<String> propertyNames) {
            return new IsPropertyAccessor(propertyNames);
        }

        @Override
        public boolean test(MethodDescriptor md) {
            return propertyNames.contains(md.getDisplayName()) &&
                    md.getMethod().getReturnType() != void.class &&
                    md.getMethod().getParameterCount() == 0;
        }
    }

    public static final class PropertyAccessor {
        private final Object theObject;
        private final String propertyName;
        private final Method readMethod;

        public PropertyAccessor(Object theObject, String propertyName, Method readMethod) {
            this.theObject = theObject;
            this.propertyName = propertyName;
            this.readMethod = readMethod;
        }

        public static PropertyAccessor fromProperty(Object theObject, PropertyDescriptor pd) {
            return new PropertyAccessor(theObject, pd.getDisplayName(), pd.getReadMethod());
        }

        public static PropertyAccessor fromMethod(Object theObject, MethodDescriptor md) {
            return new PropertyAccessor(theObject, md.getDisplayName(), md.getMethod());
        }

        public String propertyName() {
            return propertyName;
        }

        public Object propertyValue() {
            try {
                return readMethod.invoke(theObject, NO_ARGUMENTS);
            } catch (Exception e) {
                throw new IllegalArgumentException("Could not invoke " + readMethod + " on " + theObject, e);
            }
        }

        public Method readMethod() {
            return readMethod;
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
