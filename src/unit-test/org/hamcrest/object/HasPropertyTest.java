/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import junit.framework.TestCase;
import org.hamcrest.Description;
import org.hamcrest.internal.StringDescription;

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
        Description description = new StringDescription();
        hasProperty.describeTo(description);

        assertEquals("hasProperty(\"property\")", description.toString());
    }
}
