/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.beans;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.beans.HasProperty.hasProperty;

import org.hamcrest.Matcher;
import org.junit.Test;

/**
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @author Tom Denley
 * @since 1.1.0
 */
public final class HasPropertyTest {

    private final HasPropertyWithValueTest.BeanWithoutInfo bean = new HasPropertyWithValueTest.BeanWithoutInfo("a bean");

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Object> matcher = hasProperty("irrelevant");
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesWhenThePropertyExists() {
        assertMatches(hasProperty("writeOnlyProperty"), bean);
    }

    @Test public void
    doesNotMatchIfPropertyDoesNotExist() {
        assertDoesNotMatch(hasProperty("aNonExistentProp"), bean);
    }

    @Test public void
    describesItself() {
        assertDescription("hasProperty(\"property\")", hasProperty("property"));
    }

    @Test public void
    describesAMismatch() {
        assertMismatchDescription("no \"aNonExistentProp\" in <[Person: a bean]>",
                                  hasProperty("aNonExistentProp"), bean);
    }
}
