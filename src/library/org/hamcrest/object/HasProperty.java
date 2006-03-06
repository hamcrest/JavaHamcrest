/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import org.hamcrest.Matcher;

import java.beans.IntrospectionException;

/**
 * Matcher that checks that an object has a JavaBean property
 * with the specified name. If an error occurs while introspecting
 * the object then this is treated as a matcher failure.
 *
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @since 1.1.0
 */
public class HasProperty implements Matcher {

    private final String propertyName;

    public HasProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean match(Object obj) {
        try {
            return PropertyUtil.getPropertyDescriptor(propertyName, obj) != null;
        } catch (IntrospectionException e) {
            // introspection failure is treated as a matcher failure
            return false;
        }
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append("hasProperty(\"");
        buffer.append(propertyName);
        buffer.append("\")");
    }

}
