package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.core.StringHasLength.hasLength;

public class StringHasLengthTest extends AbstractMatcherTest {

    private Matcher<String> hasLength = hasLength(5);

    @Override
    protected Matcher<?> createMatcher() {
        return hasLength;
    }

    public void testMatchesLength() {
        assertMatches(hasLength, "12345");
        assertDoesNotMatch(hasLength, "1234");
        assertDoesNotMatch(hasLength, "123456");
        assertDoesNotMatch(hasLength, "");
        assertDoesNotMatch(hasLength, null);
    }
}