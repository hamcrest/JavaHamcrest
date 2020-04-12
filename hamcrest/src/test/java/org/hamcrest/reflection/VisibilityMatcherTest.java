package org.hamcrest.reflection;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.reflection.VisibilityMatchers.isPublic;

/**
 * This test is half meant to enforce the scope of the API,
 * and half meant to demonstrate one way these matchers can be used.
 */
public class VisibilityMatcherTest {
    @Test
    public void test_allConstructorsAreNotPublic() {
        for (Constructor<?> c : VisibilityMatcher.class.getConstructors()) {
            assertMatches(
                    "The constructors shouldn't be public, so that their implementation details are easy to change if need be; all we get are Matchers of reflective elements since that's all the behavior we need. Prefer the more-fluent API in VisibilityMatchers.",
                    not(isPublic()), c);
        }
    }

    @Test
    public void test_classIsNotPublic() {
        assertMatches(
                "The class shouldn't be public, so that its implementation details are easy to change if need be; all we get are Matchers of reflective elements since that's all the behavior we need. Prefer the more-fluent API in VisibilityMatchers.",
                not(isPublic()), VisibilityMatcher.class);
    }
}
