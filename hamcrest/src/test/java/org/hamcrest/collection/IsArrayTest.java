package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.collection.IsArray.array;
import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("unchecked")
public class IsArrayTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return array(equalTo("irrelevant"));
    }

    @Test
    public void testMatchesAnArrayThatMatchesAllTheElementMatchers() {
        assertMatches("should match array with matching elements",
                array(equalTo("a"), equalTo("b"), equalTo("c")), new String[]{"a", "b", "c"});
    }

    @Test
    public void testDoesNotMatchAnArrayWhenElementsDoNotMatch() {
        assertDoesNotMatch("should not match array with different elements",
                array(equalTo("a"), equalTo("b")), new String[]{"b", "c"});
    }

    @Test
    public void testDoesNotMatchAnArrayOfDifferentSize() {
        assertDoesNotMatch("should not match larger array",
                           array(equalTo("a"), equalTo("b")), new String[]{"a", "b", "c"});
        assertDoesNotMatch("should not match smaller array",
                           array(equalTo("a"), equalTo("b")), new String[]{"a"});
    }

    @Test
    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not match null",
                array(equalTo("a")), null);
    }

    @Test
    public void testHasAReadableDescription() {
        assertDescription("[\"a\", \"b\"]", array(equalTo("a"), equalTo("b")));
    }

    @Test
    public void testHasAReadableMismatchDescriptionUsing() {
        assertMismatchDescription("element <0> was \"c\"", array(equalTo("a"), equalTo("b")), new String[]{"c", "b"});
    }

    @Test
    public void testHasAReadableMismatchDescriptionUsingCustomMatchers() {
        final BaseMatcher<String> m = new BaseMatcher<String>() {
            @Override public boolean matches(Object item) { return false; }
            @Override public void describeTo(Description description) { description.appendText("c"); }
            @Override public void describeMismatch(Object item, Description description) {
                description.appendText("didn't match");
            }
        };
        assertMismatchDescription("element <0> didn't match", array(m, equalTo("b")), new String[]{"c", "b"});
    }

}
