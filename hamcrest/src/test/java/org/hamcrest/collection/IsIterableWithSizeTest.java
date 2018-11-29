package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;

public class IsIterableWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return iterableWithSize(7);
    }

    public void testMatchesEmptyIterable() throws Exception {
        assertMatches("Empty iterable", iterableWithSize(0), Collections.emptyList());
    }

    public void testMatchingSingleItemIterable() throws Exception {
        assertMatches("Single item iterable", iterableWithSize(1), Arrays.<Object>asList(1));
    }

    public void testMatchingMultipleItemIterable() throws Exception {
        assertMatches("Multiple item iterable", iterableWithSize(3), Arrays.<Object>asList(1, 2, 3));
    }

    public void testDoesNotMatchIncorrectSize() throws Exception {
        assertDoesNotMatch("Incorrect size", iterableWithSize(3), Arrays.<Object>asList(1));
    }

    public void testHasAReadableDescription() {
        assertDescription("an iterable with size <4>", iterableWithSize(4));
    }
}
