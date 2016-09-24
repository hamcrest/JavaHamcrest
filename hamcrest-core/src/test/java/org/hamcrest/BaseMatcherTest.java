package org.hamcrest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class BaseMatcherTest {

    @Test
    public void
    describesItselfWithToStringMethod() {
        Matcher<Object> someMatcher = new ConcreteBaseMacther<Object>("SOME DESCRIPTION");

        assertEquals("SOME DESCRIPTION", someMatcher.toString());
    }

    @Test
    public void equals_givenNullOtherMatcher() {
        Matcher<Object> out = new ConcreteBaseMacther<Object>("OBJECT UNDER TEST");
        boolean actual = out.equals(null);
        assertFalse(actual);
    }

    @Test
    public void equals_givenMatcherWithDifferentDescription() {
        Matcher<Object> out = new ConcreteBaseMacther<Object>("OBJECT UNDER TEST");
        Matcher<Object> otherMatcher = new ConcreteBaseMacther<Object>("OTHER MATCHER");
        boolean actual = out.equals(otherMatcher);
        assertFalse(actual);
    }

    @Test
    public void equals_givenMatcherWithSameDescription() {
        Matcher<Object> out = new ConcreteBaseMacther<Object>("OBJECT UNDER TEST");
        Matcher<Object> otherMatcher = new ConcreteBaseMacther<Object>("OBJECT UNDER TEST");
        boolean actual = out.equals(otherMatcher);
        assertTrue(actual);
    }

    private static class ConcreteBaseMacther<T> extends BaseMatcher<T> {

        private String string;

        public ConcreteBaseMacther(String string) {
            this.string = string;
        }

        @Override
        public boolean matches(Object item) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(string);
        }
    }
}
