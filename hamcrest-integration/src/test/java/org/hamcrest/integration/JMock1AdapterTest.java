package org.hamcrest.integration;

import junit.framework.TestCase;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import static org.hamcrest.core.IsEqual.equalTo;
import org.jmock.core.Constraint;

public class JMock1AdapterTest extends TestCase {

    public static interface InterfaceToMock {
        void doStuff(String name, int number);
    }

    public void testAdaptsHamcrestMatcherToJMockConstraint() {
        Constraint jMockConstraint = new JMock1Adapter(equalTo("expected"));
        assertTrue("Should have matched", jMockConstraint.eval("expected"));
        assertFalse("Should not have matched", jMockConstraint.eval("unexpected"));
    }

    public void testDelegatesDescriptionToUnderlyingMatcher() {
        Constraint jMockConstraint = new JMock1Adapter(new BaseMatcher<Object>() {
            @Override
            public boolean matches(Object o) {
                return false;
            }

            @Override
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
