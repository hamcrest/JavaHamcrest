package org.hamcrest.collection;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInTest;

public class IsArrayContainingTest extends IsInTest {
    Matcher matcher = new IsArrayContaining(new IsEqual("a"));

    public void testMatchesAnArrayThatContainsAnElementMatchingTheGivenMatcher() {
        assertMatches("should match array that contains 'a'",
                matcher, new String[]{"a", "b", "c"});
    }

    public void testDoesNotMatchAnArrayThatDoesntContainAnElementMatchingTheGivenMatcher() {
        assertDoesNotMatch("should not match array that doesn't contain 'a'",
                matcher, new String[]{"b", "c"});
        assertDoesNotMatch("should not match empty array",
                matcher, new String[0]);
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not match null", matcher, null);
    }

    public void testDoesNotMatchObjectThatIsNotAnArray() {
        assertDoesNotMatch("should not match empty list", matcher, "not a collection");
    }

    public void testMatchesPrimitiveArrayElements() {
        assertMatches("boolean", new IsArrayContaining(new IsEqual(Boolean.TRUE)), new boolean[]{true, false});
        assertDoesNotMatch("boolean", new IsArrayContaining(new IsEqual(Boolean.TRUE)), new boolean[]{false});

        assertMatches("byte", new IsArrayContaining(new IsEqual(new Byte((byte) 1))), new byte[]{1, 2, 3});
        assertDoesNotMatch("byte", new IsArrayContaining(new IsEqual(new Byte((byte) 0))), new byte[]{1, 2, 3});

        assertMatches("char", new IsArrayContaining(new IsEqual(new Character((char) 1))), new char[]{1, 2, 3});
        assertDoesNotMatch("char", new IsArrayContaining(new IsEqual(new Character((char) 0))), new char[]{1, 2, 3});

        assertMatches("short", new IsArrayContaining(new IsEqual(new Short((short) 1))), new short[]{1, 2, 3});
        assertDoesNotMatch("short", new IsArrayContaining(new IsEqual(new Short((short) 0))), new short[]{1, 2, 3});

        assertMatches("int", new IsArrayContaining(new IsEqual(new Integer(1))), new int[]{1, 2, 3});
        assertDoesNotMatch("int", new IsArrayContaining(new IsEqual(new Integer(0))), new int[]{1, 2, 3});

        assertMatches("long", new IsArrayContaining(new IsEqual(new Long(1))), new long[]{1, 2, 3});
        assertDoesNotMatch("long", new IsArrayContaining(new IsEqual(new Long(0))), new long[]{1, 2, 3});

        assertMatches("float", new IsArrayContaining(new IsEqual(new Float(1f))), new float[]{1f, 2f, 3f});
        assertDoesNotMatch("float", new IsArrayContaining(new IsEqual(new Float(0f))), new float[]{1f, 2f, 3f});

        assertMatches("double", new IsArrayContaining(new IsEqual(new Double(1.0))), new double[]{1.0, 2.0, 3.0});
        assertDoesNotMatch("double", new IsArrayContaining(new IsEqual(new Double(0.0))), new double[]{1.0, 2.0, 3.0});
    }

    public void testHasAReadableDescription() {
        Matcher matcher = new IsArrayContaining(new IsEqual("a"));

        assertDescription("an array containing eq(\"a\")", matcher);
    }
}
