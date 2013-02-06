package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;

public class IsArrayContainingTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasItemInArray("irrelevant");
    }

    public void testMatchesAnArrayThatContainsAnElementMatchingTheGivenMatcher() {
        assertMatches("should match array that contains 'a'",
                hasItemInArray("a"), new String[]{"a", "b", "c"});
    }

    public void testDoesNotMatchAnArrayThatDoesntContainAnElementMatchingTheGivenMatcher() {
        assertDoesNotMatch("should not match array that doesn't contain 'a'",
                hasItemInArray("a"), new String[]{"b", "c"});
        assertDoesNotMatch("should not match empty array",
                hasItemInArray("a"), new String[0]);
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not match null",
                hasItemInArray("a"), null);
    }

    public void testHasAReadableDescription() {
        assertDescription("an array containing \"a\"", hasItemInArray("a"));
    }

    public void testDoesNotMatchObjectThatIsNotAnArray() {
        assertDoesNotMatch("should not match empty list",
            hasItemInArray("a"), "not a collection");
    }

    // This test existed before Hamcrest was converted to use 
    // generics. The current implementation does not support primitive
    // arrays (the match fails because parameter is not of correct 
    // type), but this could be made to work.

    //public void testMatchesPrimitiveArrayElements() {
    //    assertMatches("boolean", hasItemInArray(true), new boolean[]{true, false});
    //    assertDoesNotMatch("boolean", hasItemInArray(true), new boolean[]{false});
    //
    //    assertMatches("byte", hasItemInArray((byte)1), new byte[]{1, 2, 3});
    //    assertDoesNotMatch("byte", hasItemInArray((byte)0), new byte[]{1, 2, 3});
    //
    //    assertMatches("char", hasItemInArray('a'), new char[]{'a', 'b', 'c'});
    //    assertDoesNotMatch("char", hasItemInArray('z'), new char[]{'a', 'b', 'c'});
    //
    //    assertMatches("short", hasItemInArray((short)1), new short[]{1, 2, 3});
    //    assertDoesNotMatch("short", hasItemInArray((short)0), new short[]{1, 2, 3});
    //
    //    assertMatches("int", hasItemInArray(1), new int[]{1, 2, 3});
    //    assertDoesNotMatch("int", hasItemInArray(0), new int[]{1, 2, 3});
    //
    //    assertMatches("long", hasItemInArray(1L), new long[]{1, 2, 3});
    //    assertDoesNotMatch("long", hasItemInArray(0L), new long[]{1, 2, 3});
    //
    //    assertMatches("float", hasItemInArray(1f), new float[]{1f, 2f, 3f});
    //    assertDoesNotMatch("float", hasItemInArray(0f), new float[]{1f, 2f, 3f});
    //
    //    assertMatches("double", hasItemInArray(1.0), new double[]{1.0, 2.0, 3.0});
    //    assertDoesNotMatch("double", hasItemInArray(0.0), new double[]{1.0, 2.0, 3.0});
    //}
}
