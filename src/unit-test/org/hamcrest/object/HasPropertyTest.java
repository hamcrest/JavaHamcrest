/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.object.HasProperty.hasProperty;

/**
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @since 1.1.0
 */
public class HasPropertyTest extends AbstractMatcherTest {

    private final HasPropertyWithValueTest.BeanWithoutInfo bean
            = new HasPropertyWithValueTest.BeanWithoutInfo("a bean");

    public void testReturnsTrueIfPropertyExists() {
        assertThat(bean, hasProperty("writeOnlyProperty"));
    }

    public void testReturnsFalseIfPropertyDoesNotExist() {
        assertThat(bean, not(hasProperty("aNonExistentProp")));
    }

    public void testDescribeTo() {
        assertDescription("hasProperty(\"property\")", hasProperty("property"));
    }
}
