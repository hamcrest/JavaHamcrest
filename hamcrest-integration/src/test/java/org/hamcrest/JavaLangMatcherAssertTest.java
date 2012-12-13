package org.hamcrest;

import static org.hamcrest.JavaLangMatcherAssert.that;
import static org.hamcrest.core.StringStartsWith.startsWith;
import junit.framework.TestCase;

public class JavaLangMatcherAssertTest extends TestCase {
    public void testWouldCauseFailingAssertion() throws Exception {
        assertEquals(false, that("Foo", startsWith("Bar")));
    }
    
    public void testWouldCausePassingAssertion() throws Exception {
        assertEquals(true, that("Foo", startsWith("F")));
    }
}
