package org.hamcrest.examples.junit3;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import junit.framework.TestCase;

/**
 * Demonstrates how HamCrest matchers can be used from EasyMock with
 * JUnit 3.x.
 *
 * @author Joe Walnes
 */
public class ExampleWithEasyMock2 extends TestCase {

    /* EasyMock (2) specific notes:
     *
     * Important terminology:
     *   What EasyMock2 calls 'IArgumentMatcher', Hamcrest calls 'Matchers'.
     *
     * This functionality is available for EasyMock 2.x but NOT EasyMock 1.x.
     *
     * The class extends the standard JUnit TestCase.
     *
     * The additional Hamcrest Matchers can be used by using a static import:
     *   import static org.hamcrest.EasyMockMatchers.*;
     *
     * This provides the Hamcrest library of Matchers through an interface
     * that will provide an adapter to EasyMock IArgumentMatchers and report
     * them to EasyMock as it needs to keep track of them.
     */


    /**
     * A sample interface that will be mocked.
     */
    public static interface AnInterface {
        void doStuff(String string);
    }

    private AnInterface mock = createMock(AnInterface.class);

    /**
     * This examples shows using a mock with a standard EasyMock2 matcher.
     * Hamcrest is not used here.
     */
    public void testUsingAnEasyMockMatcher() {
        mock.doStuff(eq("i like cheese and stuff"));
        replay(mock);
        mock.doStuff("i like cheese and stuff");
        verify(mock);
    }

    /**
     * This examples shows using a mock with a Hamcrest matcher, adapted
     * to jMock.
     */
    public void testUsingAHamcrestMatcher() {
        mock.doStuff(equalTo("xx"));
        replay(mock);
        mock.doStuff("xx");
        verify(mock);
    }

    /**
     * This examples shows using the standard jMock assertThat() method
     * with both jMock Constraints and Hamcrest Matchers.
     */
    public void testUsingAssertThat() {
        // TODO(joe): This is a bit tricker with EasyMock.
        //assertThat("xx", equal("xx"));
        //assertThat("yy", not(equal("xx")));
        //assertThat("i like cheese", startsWith("i like"));
    }

}
