package org.hamcrest.integration;

import junit.framework.TestCase;
import static org.hamcrest.Matchers.*;
import org.hamcrest.Matcher;
import org.hamcrest.Description;
import org.jmock.core.Constraint;

public class JMockAdapterTest extends TestCase {

    public static interface InterfaceToMock {
        void doStuff(String name, int number);
    }

    public void testAdaptsHamcrestMatcherToJMockConstraint() {
        Constraint jMockConstraint = new JMockAdapter(eq("expected"));
        assertTrue("Should have matched", jMockConstraint.eval("expected"));
        assertFalse("Should not have matched", jMockConstraint.eval("unexpected"));
    }

    public void testDelegatesDescriptionToUnderlyingMatcher() {
        Constraint jMockConstraint = new JMockAdapter(new Matcher() {
            public boolean match(Object o) {
                return false;
            }

            public void describeTo(Description description) {
                description.appendText("is like ");
                description.appendValue("cheese");
            }
        });

        StringBuffer buffer = new StringBuffer();
        buffer = jMockConstraint.describeTo(buffer);
        assertEquals("is like \"cheese\"", buffer.toString());
    }

}
