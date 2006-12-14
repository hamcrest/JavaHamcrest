package org.hamcrest.examples.junit3;

import junit.framework.TestCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.StringContains.containsString;

/**
 * Demonstrates how HamCrest matchers can be used with assertThat()
 * using JUnit 3.8.x.
 *
 * @author Joe Walnes
 */
public class ExampleWithAssertThat extends TestCase {

    public void testUsingAssertThat() {
        assertThat("xx", equalTo("xx"));
        assertThat("yy", not(equalTo("xx")));
        assertThat("i like cheese", containsString("cheese"));
    }

}
