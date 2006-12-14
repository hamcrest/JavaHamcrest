package org.hamcrest.examples.testng;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.StringContains.containsString;
import org.testng.annotations.Test;

/**
 * Demonstrates how Hamcrest matchers can be used with assertThat()
 * using TestNG.
 *
 * @author Joe Walnes
 */
@Test
public class ExampleWithAssertThat {

    @Test
    public void usingAssertThat() {
        assertThat("xx", equalTo("xx"));
        assertThat("yy", not(equalTo("xx")));
        assertThat("i like cheese", containsString("cheese"));
    }

}
