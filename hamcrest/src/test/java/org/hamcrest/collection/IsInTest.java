package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.test.MatcherAssertions.assertDoesNotMatch;
import static org.hamcrest.test.MatcherAssertions.assertMatches;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsInTest extends AbstractMatcherTest {

    String[] elements = {"a", "b", "c"};

    @Override
    protected Matcher<?> createMatcher() {
        return new IsIn<>(elements);
    }

    @Test
    public void testReturnsTrueIfArgumentIsInCollection() {
        Collection<String> collection = Arrays.asList(elements);
        Matcher<String> isIn = new IsIn<>(collection);

        assertMatches("a", isIn, "a");
        assertMatches("b", isIn, "b");
        assertMatches("c", isIn, "c");
        assertDoesNotMatch("d", isIn, "d");
    }

    @Test
    public void testReturnsTrueIfArgumentIsInArray() {
        Matcher<String> isIn = new IsIn<>(elements);

        assertMatches("a", isIn, "a");
        assertMatches("b", isIn, "b");
        assertMatches("c", isIn, "c");
        assertDoesNotMatch("d", isIn, "d");
    }

    @Test
    public void testHasReadableDescription() {
        Matcher<String> isIn = new IsIn<>(elements);

        assertEquals(
                "one of {\"a\", \"b\", \"c\"}",
                StringDescription.toString(isIn),
                "description");
    }

}
