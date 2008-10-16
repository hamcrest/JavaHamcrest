package org.hamcrest;

import static org.hamcrest.JavaLangMatcherAssert.that;
import static org.hamcrest.core.StringStartsWith.startsWith;
import junit.framework.TestCase;

public class JavaLangMatcherAssertTest extends TestCase {
    static boolean assertsEnabled = false;
    static {
      assert (assertsEnabled = true);  // Intentional side-effect!!!
    }

    public void testFailingAssertion() throws Exception {
        boolean thrown = false;
        try {
            assert that("Foo", startsWith("Bar"));
            // Can't fail() here since catch block will catch JUnit exception.
        } catch (AssertionError expected) {
            thrown = true;
        }
        if (assertsEnabled && ! thrown) {
            fail();
        }
    }

    public void testPassingAssertion() throws Exception {
        assert that("Foo", startsWith("F"));
    }
}
