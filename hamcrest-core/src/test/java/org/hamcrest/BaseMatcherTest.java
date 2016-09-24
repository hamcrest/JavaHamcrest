package org.hamcrest;

import org.hamcrest.core.AllOf;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class BaseMatcherTest {

    @Test
    public void
    describesItselfWithToStringMethod() {
        Matcher<Object> someMatcher = new ConcreteBaseMacther<Object>("SOME DESCRIPTION");

        assertEquals("SOME DESCRIPTION", someMatcher.toString());
    }
    
    @Test
    public void and() {
        Matcher<Object> first = new ConcreteBaseMacther<Object>("SOME MATCHER");
        Matcher<Object> second = new ConcreteBaseMacther<Object>("SOME OTHER MATCHER");
        Matcher<Object> actualAnd = first.and(second);
        Matcher<Object> expectedAnd = AllOf.<Object>allOf(first, second);
        assertEquals(expectedAnd.toString(), actualAnd.toString());
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
