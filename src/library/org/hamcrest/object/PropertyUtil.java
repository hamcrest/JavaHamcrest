/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Utility class for accessing properties on JavaBean objects.
 * <p/>
 * See http://java.sun.com/products/javabeans/docs/index.html for
 * more information on JavaBeans.
 *
 * @author Iain McGinniss
 * @since 1.1.0
 */
public class PropertyUtil {

    /**
     * Returns the description of the property with the provided
     * name on the provided object's interface.
     *
     * @return the description of the property, or null if the
     *         property does not exist.
     * @throws IntrospectionException if an error occured using
     *                                the JavaBean Introspector class to query the properties
     *                                of the provided class.
     */
    public static PropertyDescriptor getPropertyDescriptor(String propertyName, Object fromObj) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(fromObj.getClass());
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

        for (int i = 0; i < properties.length; i++) {
            if (properties[i].getName().equals(propertyName)) {
                return properties[i];
            }
        }

        return null;
    }
}
