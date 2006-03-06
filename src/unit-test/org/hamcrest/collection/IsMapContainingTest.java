package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;

import java.util.HashMap;
import java.util.Map;

public class IsMapContainingTest extends AbstractMatcherTest {
    private static final IsAnything ANYTHING = IsAnything.INSTANCE;

    public void testMatchesMapContainingMatchingKeyAndValue() {
        Map map = new HashMap();
        map.put("a", "A");
        map.put("b", "B");

        Matcher matcherA = new IsMapContaining(new IsEqual("a"), new IsEqual("A"));
        Matcher matcherB = new IsMapContaining(new IsEqual("b"), new IsEqual("B"));
        Matcher matcherC = new IsMapContaining(new IsEqual("c"), new IsEqual("C"));

        assertMatches("matcherA", matcherA, map);
        assertMatches("matcherB", matcherB, map);
        assertDoesNotMatch("matcherC", matcherC, map);
    }

    public void testDoesNotMatchAnObjectThatIsNotAMap() {
        assertDoesNotMatch("should not match a string",
                new IsMapContaining(ANYTHING, ANYTHING), "not a map");
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not match a string",
                new IsMapContaining(ANYTHING, ANYTHING), null);
    }

    public void testHasReadableDescription() {
        Matcher matcher = new IsMapContaining(new IsEqual("a"), new IsEqual("A"));

        assertDescription("map containing [eq(\"a\")->eq(\"A\")]", matcher);
    }
}
