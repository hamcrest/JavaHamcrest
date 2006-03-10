/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Description;
import org.hamcrest.internal.StringDescription;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsSame;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @since 1.1.0
 */
public class HasPropertyWithValueTest extends AbstractMatcherTest {
    final private BeanWithoutInfo shouldMatch = new BeanWithoutInfo("is expected");
    final private BeanWithoutInfo shouldNotMatch = new BeanWithoutInfo("not expected");
    final private BeanWithInfo beanWithInfo = new BeanWithInfo("with info");

    public void testMatchesInfolessBeanWithMatchedNamedProperty() {
        HasPropertyWithValue hasProperty = new HasPropertyWithValue("property", new IsSame("is expected"));

        assertTrue(hasProperty.match(shouldMatch));
        assertFalse(hasProperty.match(shouldNotMatch));
    }

    public void testMatchesBeanWithInfoWithMatchedNamedProperty() {
        HasPropertyWithValue hasProperty = new HasPropertyWithValue("property", new IsSame("with info"));
        assertTrue(hasProperty.match(beanWithInfo));
    }

    public void testDoesNotMatchInfolessBeanWithoutMatchedNamedProperty() {
        HasPropertyWithValue hasProperty = new HasPropertyWithValue("nonExistentProperty", new IsAnything());
        assertFalse(hasProperty.match(shouldNotMatch));
    }

    public void testDoesNotMatchWriteOnlyProperty() {
        HasPropertyWithValue hasProperty = new HasPropertyWithValue("writeOnlyProperty", new IsAnything());
        assertFalse(hasProperty.match(shouldNotMatch));
    }

    public void testDescribeTo() {
        IsEqual isEqual = new IsEqual(Boolean.TRUE);
        Description isEqualDescription = new StringDescription();
        isEqual.describeTo(isEqualDescription);
        HasPropertyWithValue hasProperty = new HasPropertyWithValue("property", new IsEqual(Boolean.TRUE));

        assertDescription("hasProperty(\"property\", " + isEqualDescription + ")", hasProperty);
    }

    public static class BeanWithoutInfo {
        private String property;

        public BeanWithoutInfo(String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public void setWriteOnlyProperty(float property) {
        }

        public String toString() {
            return "[Person: " + property + "]";
        }
    }

    public static class BeanWithInfo {
        private String propertyValue;

        public BeanWithInfo(String propertyValue) {
            this.propertyValue = propertyValue;
        }

        public String property() {
            return propertyValue;
        }
    }

    public static class BeanWithInfoBeanInfo extends SimpleBeanInfo {
        public PropertyDescriptor[] getPropertyDescriptors() {
            try {
                return new PropertyDescriptor[]{
                        new PropertyDescriptor("property", BeanWithInfo.class, "property", null)
                };
            } catch (IntrospectionException e) {
                throw new RuntimeException("Introspection exception: " + e.getMessage());
            }
        }
    }
}
