package org.hamcrest.examples.junit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

/**
 * Demonstrates how HamCrest matchers can be used with assertThat()
 * using JUnit 4.x.
 *
 * @author Joe Walnes
 */
public class ExampleWithAssertThat {

    @Test
    public void usingAssertThat() {
        assertThat("xx", is("xx"));
        assertThat("yy", is(not("xx")));
        assertThat("i like cheese", containsString("cheese"));
    }

    /**
     * Allow JUnit 4 test to be run under JUnit 3.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(ExampleWithAssertThat.class);
    }

}
