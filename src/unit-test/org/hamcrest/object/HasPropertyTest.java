/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import junit.framework.TestCase;

/**
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @since 1.1.0
 */
public class HasPropertyTest extends TestCase {

    private final HasPropertyWithValueTest.BeanWithoutInfo bean = new HasPropertyWithValueTest.BeanWithoutInfo("a bean");

    public void testReturnsTrueIfPropertyExists() {
        HasProperty hasProperty = new HasProperty("writeOnlyProperty");

        assertTrue(hasProperty.match(bean));
    }

    public void testReturnsFalseIfPropertyDoesNotExist() {
        HasProperty hasProperty = new HasProperty("aNonExistentProp");

        assertFalse(hasProperty.match(bean));
    }

    public void testDescribeTo() {
        HasProperty hasProperty = new HasProperty("property");
        StringBuffer buffer = new StringBuffer();
        hasProperty.describeTo(buffer);

        assertEquals("hasProperty(\"property\")", buffer.toString());
    }
}
