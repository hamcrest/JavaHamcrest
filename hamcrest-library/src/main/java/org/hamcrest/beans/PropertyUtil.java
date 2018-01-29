package org.hamcrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

/**
 * Utility class for accessing properties on JavaBean objects.
 * See http://java.sun.com/products/javabeans/docs/index.html for
 * more information on JavaBeans.
 *
 * @author Iain McGinniss
 * @author Steve Freeman
 * @since 1.1.0
 */
public class PropertyUtil {
    /**
     * Returns the description of the property with the provided
     * name on the provided object's interface.
     *
     * @return the descriptor of the property, or null if the property does not exist.
     * @throws IllegalArgumentException if there's a introspection failure
     */
    public static PropertyDescriptor getPropertyDescriptor(String propertyName, Object fromObj) throws IllegalArgumentException {
        for (PropertyDescriptor property : propertyDescriptorsFor(fromObj, null)) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }

        return null;
    }

    public static PropertyDescriptor getPropertyDescriptor4Boolean(String propertyName, Object fromObj) throws IllegalArgumentException {
        try {
            for (MethodDescriptor method : Introspector.getBeanInfo(fromObj.getClass(), Object.class).getMethodDescriptors()) {
                if (method.getName().equals(getBooleanGetterName4Property(propertyName))) {
                    return new PropertyDescriptor(propertyName, method.getMethod(), null);
                }
            }

            return null;
        } catch (IntrospectionException e) {
            return null;
        }
    }

    public static String getBooleanGetterName4Property(String propertyName) {
        if(propertyName == null || propertyName.length() == 0) {
            return null;
        }
        return "is" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    /**
     * Returns all the property descriptors for the class associated with the given object
     *
     * @param fromObj Use the class of this object
     * @param stopClass Don't include any properties from this ancestor class upwards.
     * @return Property descriptors
     * @throws IllegalArgumentException if there's a introspection failure
     */
    public static PropertyDescriptor[] propertyDescriptorsFor(Object fromObj, Class<Object> stopClass) throws IllegalArgumentException {
      try {
        return Introspector.getBeanInfo(fromObj.getClass(), stopClass).getPropertyDescriptors();
      } catch (IntrospectionException e) {
        throw new IllegalArgumentException("Could not get property descriptors for " + fromObj.getClass(), e);
      }
    }

    public static final Object[] NO_ARGUMENTS = new Object[0];
}
