package org.hamcrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class to help with finding properties in an object.
 * <p>
 * The properties can be either properties as described by the
 * JavaBean specification and APIs, or it will fall back to finding
 * fields with corresponding methods, enabling the property matchers
 * to work with newer classes like Records.
 */
public class PropertyAccessor {
    private final Object beanLikeObject;
    private final SortedMap<String, PropertyReadLens> readLenses;

    /**
     * Constructor.
     * @param beanLikeObject the object to search for properties.
     */
    public PropertyAccessor(Object beanLikeObject) {
        this.beanLikeObject = beanLikeObject;
        this.readLenses = new TreeMap<>(makeLensesFor(beanLikeObject));
    }

    private Map<String, PropertyReadLens> makeLensesFor(Object bean) {
        PropertyDescriptor[] properties = PropertyUtil.propertyDescriptorsFor(bean, Object.class);
        if (properties != null && properties.length > 0) {
            return makePropertyLensesFrom(properties);
        }

        return makeFieldMethodLensesFor(bean);
    }

    private Map<String, PropertyReadLens> makePropertyLensesFrom(PropertyDescriptor[] descriptors) {
        return Arrays.stream(descriptors)
                .map(pd -> new PropertyReadLens(pd.getDisplayName(), pd.getReadMethod()))
                .collect(Collectors.toMap(PropertyReadLens::getName, Function.identity()));
    }

    private Map<String, PropertyReadLens> makeFieldMethodLensesFor(Object bean) {
        try {
            Set<String> fieldNames = getFieldNames(bean);
            MethodDescriptor[] methodDescriptors = Introspector.getBeanInfo(bean.getClass(), null).getMethodDescriptors();
            return Arrays.stream(methodDescriptors)
                    .filter(IsPropertyAccessor.forOneOf(fieldNames))
                    .map(md -> new PropertyReadLens(md.getDisplayName(), md.getMethod()))
                    .collect(Collectors.toMap(PropertyReadLens::getName, Function.identity()));
        }
        catch (IntrospectionException e) {
            throw new IllegalArgumentException("Could not get method descriptors for " + bean.getClass(), e);
        }
    }

    /**
     * The names of properties that were found in the object.
     * @return a set of field names
     */
    public Set<String> fieldNames() {
        return readLenses.keySet();
    }

    /**
     * The collection of lenses for all the properties that were found in the
     * object.
     * @return the collection of lenses
     */
    public Collection<PropertyReadLens> readLenses() {
        return readLenses.values();
    }

    /**
     * The read lens for the specified property.
     * @param propertyName the property to find the lens for.
     * @return the read lens for the property
     */
    public PropertyReadLens readLensFor(String propertyName) {
        return readLenses.get(propertyName);
    }

    /**
     * The value of the specified property.
     * @param propertyName the name of the property
     * @return the value of the given property name.
     */
    public Object fieldValue(String propertyName) {
        PropertyReadLens lens = readLenses.get(propertyName);
        if (lens == null) {
            String message = String.format("Unknown property '%s' for bean '%s'", propertyName, beanLikeObject);
            throw new IllegalArgumentException(message);
        }
        return lens.getValue();
    }

    /**
     * Returns the field names of the given object.
     * It can be the names of the record components of Java Records, for example.
     *
     * @param fromObj the object to check
     * @return The field names
     * @throws IllegalArgumentException if there's a security issue reading the fields
     */
    private static Set<String> getFieldNames(Object fromObj) throws IllegalArgumentException {
        try {
            return Arrays.stream(fromObj.getClass().getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toSet());
        } catch (SecurityException e) {
            throw new IllegalArgumentException("Could not get record component names for " + fromObj.getClass(), e);
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

    /**
     * Encapsulates a property in the parent object.
     */
    public class PropertyReadLens {
        private final String name;
        private final Method readMethod;

        /**
         * Constructor.
         * @param name the name of the property
         * @param readMethod the method that can be used to get the value of the property
         */
        public PropertyReadLens(String name, Method readMethod) {
            this.name = name;
            this.readMethod = readMethod;
        }

        /**
         * The name of the property
         * @return the name of the property.
         */
        public String getName() {
            return name;
        }

        /**
         * The read method for the property.
         * @return the read method for the property.
         */
        public Method getReadMethod() {
            return readMethod;
        }

        /**
         * The value of the property.
         * @return the value of the property.
         */
        public Object getValue() {
            Object bean = PropertyAccessor.this.beanLikeObject;
            try {
                return readMethod.invoke(bean, PropertyUtil.NO_ARGUMENTS);
            } catch (Exception e) {
                throw new IllegalArgumentException("Could not invoke " + readMethod + " on " + bean, e);
            }
        }
    }
}
