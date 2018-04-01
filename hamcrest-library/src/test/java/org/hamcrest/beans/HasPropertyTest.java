package org.hamcrest.beans;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.beans.HasProperty.hasProperty;

/**
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @author Tom Denley
 * @since 1.1.0
 */
public final class HasPropertyTest {

    private final HasPropertyWithValueTest.BeanWithoutInfo bean = new HasPropertyWithValueTest.BeanWithoutInfo("a bean", false);

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Object> matcher = hasProperty("irrelevant");
        
        assertNullSafe(matcher);
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
