package org.hamcrest.integration;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jmock.core.Constraint;
import org.junit.Test;

public final class JMock1AdapterTest {

    public static interface InterfaceToMock {
        void doStuff(String name, int number);
    }

    @Test public void
    adaptsHamcrestMatcherToJMockConstraint() {
        Constraint jMockConstraint = new JMock1Adapter(equalTo("expected"));
        assertTrue("Should have matched", jMockConstraint.eval("expected"));
        assertFalse("Should not have matched", jMockConstraint.eval("unexpected"));
    }

    @Test public void
    delegatesDescriptionToUnderlyingMatcher() {
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
