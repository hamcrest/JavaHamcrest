package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsArrayWithSize.emptyArray;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsArrayWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return arrayWithSize(equalTo(2));
    }

    @Test
    public void testMatchesWhenSizeIsCorrect() {
        assertMatches("correct size", arrayWithSize(equalTo(3)), new Object[] {1, 2, 3});
        assertDoesNotMatch("incorrect size", arrayWithSize(equalTo(2)), new Object[] {1, 2, 3});
    }

    @Test
    public void testProvidesConvenientShortcutForArrayWithSizeEqualTo() {
        assertMatches("correct size", arrayWithSize(3), new Object[] {1, 2, 3});
        assertDoesNotMatch("incorrect size", arrayWithSize(2), new Object[] {1, 2, 3});
    }

    @Test
    public void testEmptyArray() {
        assertMatches("correct size", emptyArray(), new Object[] {});
        assertDoesNotMatch("incorrect size", emptyArray(), new Object[] {1});
    }

    @Test
    public void testHasAReadableDescription() {
        assertDescription("an array with size <3>", arrayWithSize(equalTo(3)));
        assertDescription("an empty array", emptyArray());
    }

}
