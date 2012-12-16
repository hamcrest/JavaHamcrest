package org.hamcrest;

import static org.hamcrest.JavaLangMatcherAssert.that;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class JavaLangMatcherAssertTest {

    @Test public void
    wouldCauseFailingAssertion() throws Exception {
        assertFalse(that("Foo", startsWith("Bar")));
    }

    @Test public void
    wouldCausePassingAssertion() throws Exception {
        assertTrue(that("Foo", startsWith("F")));
    }
}
