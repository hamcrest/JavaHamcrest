package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;

public class IsInTest extends AbstractMatcherTest {
    String[] elements = {"a", "b", "c"};

    public void testReturnsTrueIfArgumentIsInCollection() {
        Collection collection = Arrays.asList(elements);
        Matcher in = new IsIn(collection);

        assertMatches("a", in, "a");
        assertMatches("b", in, "b");
        assertMatches("c", in, "c");
        assertDoesNotMatch("d", in, "d");
    }

    public void testReturnsTrueIfArgumentIsInArray() {
        Matcher in = new IsIn(elements);

        assertMatches("a", in, "a");
        assertMatches("b", in, "b");
        assertMatches("c", in, "c");
        assertDoesNotMatch("d", in, "d");
    }

    public void testHasReadableDescription() {
        Matcher in = new IsIn(elements);

        assertDescription("one of {\"a\", \"b\", \"c\"}", in);
    }
}
