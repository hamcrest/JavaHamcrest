package org.hamcrest.examples.testng;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isTwoXs;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.StringContains.containsString;
import org.testng.annotations.Test;

/**
 * Demonstrates how HamCrest matchers can be used with assertThat()
 * using TestNG.
 *
 * @author Joe Walnes
 */
@Test
public class ExampleWithAssertThat {

    @Test
    public void usingAssertThat() {
        assertThat("xx", isTwoXs());
        assertThat("yy", not(isTwoXs()));
        assertThat("i like cheese", containsString("cheese"));
    }

}
