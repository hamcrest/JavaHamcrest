package org.hamcrest.beans;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.test.MatcherAssertions.*;
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
    private final HasPropertyWithValueTest.RecordLikeBeanWithoutInfo record = new HasPropertyWithValueTest.RecordLikeBeanWithoutInfo("a record", false);

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Object> matcher = hasProperty("irrelevant");

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesWhenThePropertyExists() {
        assertMatches(hasProperty("writeOnlyProperty"), bean);
        assertMatches(hasProperty("property"), record);
    }

    @Test public void
    doesNotMatchIfPropertyDoesNotExist() {
        assertDoesNotMatch(hasProperty("aNonExistentProp"), bean);
        assertDoesNotMatch(hasProperty("aNonExistentProp"), record);
        assertDoesNotMatch(hasProperty("notAGetterMethod"), record);
    }

    @Test public void
    describesItself() {
        assertDescription("hasProperty(\"property\")", hasProperty("property"));
    }

    @Test public void
    describesAMismatch() {
        assertMismatchDescription("no \"aNonExistentProp\" in <[Person: a bean]>",
                                  hasProperty("aNonExistentProp"), bean);
        assertMismatchDescription("no \"aNonExistentProp\" in <[Person: a record]>",
                                  hasProperty("aNonExistentProp"), record);
    }

}
