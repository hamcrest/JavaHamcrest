package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Function;
import org.hamcrest.Matcher;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.core.ExtractMatcher.valueOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class ExtractMatcherTest {
    private static final String FUNCTION_DESCRIPTION = "value";
    private static final String MATCHER_DESCRIPTION = "is null";
    private static final Matcher MATCHER = is(nullValue());

    private ExtractMatcher extractMatcher = new ExtractMatcher(new Function() {
        @Override
        public Object apply(Object value) {
            return value;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(FUNCTION_DESCRIPTION);
        }
    }, MATCHER);

    @Test public void
    appendsMatcherToFunctionDescription() throws Exception {
        assertDescription(FUNCTION_DESCRIPTION + " " + MATCHER_DESCRIPTION, extractMatcher);
    }

    @Test public void
    mismatchShouldPrependTheFunctionDescription() throws Exception {
        assertMismatchDescription(FUNCTION_DESCRIPTION + " was <1>", extractMatcher, 1);
    }

    @Test public void
    happyTestCase () {
        assertTrue(valueOf(total(), is(0)).matches(new TestObject(0)));
    }
    @Test public void
    sadTestCase () {
        assertFalse(valueOf(total(), is(0)).matches(new TestObject(1)));
    }

    private Function<TestObject, Integer> total() {
        return new Function<TestObject, Integer>() {
            @Override
            public Integer apply(TestObject value) {
                return value.total;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("total");
            }
        };
    }

    private class TestObject {
        int total = 0;

        public TestObject (int val) {
            total = val;
        }
    }
}
