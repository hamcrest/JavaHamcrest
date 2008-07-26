package org.hamcrest;

import static org.hamcrest.JavaLangMatcherAssert.that;
import static org.hamcrest.text.StringStartsWith.startsWith;
import junit.framework.TestCase;

public class JavaLangMatcherAssertTest extends TestCase {

    public void testFailingAssertion() throws Exception {
        boolean thrown;
        try {
            assert that("Foo", startsWith("Bar"));
            // Can't fail() here since catch block will catch JUnit exception.
            thrown = false;
        } catch (AssertionError expected) {
            thrown = true;
        }
        if (thrown) {
            fail();
        }
    }

    public void testPassingAssertion() throws Exception {
        assert that("Foo", startsWith("Far"));
    }
}
