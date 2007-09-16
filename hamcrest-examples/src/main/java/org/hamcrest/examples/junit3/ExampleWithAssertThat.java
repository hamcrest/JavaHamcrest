package org.hamcrest.examples.junit3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import junit.framework.TestCase;

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
