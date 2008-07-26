package org.hamcrest.collection;

import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsArrayWithSize.emptyArray;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsArrayWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return arrayWithSize(equalTo(2));
    }

    public void testMatchesWhenSizeIsCorrect() {
        assertMatches("correct size", arrayWithSize(equalTo(3)), new Object[] {1, 2, 3});
        assertDoesNotMatch("incorrect size", arrayWithSize(equalTo(2)), new Object[] {1, 2, 3});
    }

    public void testProvidesConvenientShortcutForArrayWithSizeEqualTo() {
        assertMatches("correct size", arrayWithSize(3), new Object[] {1, 2, 3});
        assertDoesNotMatch("incorrect size", arrayWithSize(2), new Object[] {1, 2, 3});
    }

    public void testEmptyArray() {
        assertMatches("correct size", emptyArray(), new Object[] {});
        assertDoesNotMatch("incorrect size", emptyArray(), new Object[] {1});
    }

    public void testHasAReadableDescription() {
        assertDescription("an array with size <3>", arrayWithSize(equalTo(3)));
        assertDescription("an empty array", emptyArray());
    }
}
