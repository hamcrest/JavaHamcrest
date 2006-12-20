/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasProperty.hasProperty;
import static org.hamcrest.core.IsNot.not;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

/**
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @since 1.1.0
 */
public class HasPropertyTest extends AbstractMatcherTest {

    private final HasPropertyWithValueTest.BeanWithoutInfo bean
            = new HasPropertyWithValueTest.BeanWithoutInfo("a bean");

    protected Matcher<?> createMatcher() {
        return hasProperty("irrelevant");
    }

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
