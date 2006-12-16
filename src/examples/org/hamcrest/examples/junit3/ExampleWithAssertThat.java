package org.hamcrest.examples.junit3;

import junit.framework.TestCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Demonstrates how Hamcrest matchers can be used with assertThat()
 * using JUnit 3.8.x.
 *
 * @author Joe Walnes
 */
public class ExampleWithAssertThat extends TestCase {

    public void testUsingAssertThat() {
        assertThat("xx", is("xx"));
        assertThat("yy", is(not("xx")));
        assertThat("i like cheese", containsString("cheese"));
    }

}
