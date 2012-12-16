package org.hamcrest.integration;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.IArgumentMatcher;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;

public final class EasyMock2AdapterTest {

    public static interface InterfaceToMock {
        void doStuff(String name, int number);
    }

    @Test public void
    adaptsHamcrestMatcherToEasyMockArgumentsMatcher() {
        IArgumentMatcher easyMockMatcher = new EasyMock2Adapter(equalTo("expected"));
        assertTrue("Should have matched", easyMockMatcher.matches("expected"));
        assertFalse("Should not have matched", easyMockMatcher.matches("unexpected"));
    }

    @Test public void
    delegatesDescriptionToUnderlyingMatcher() {
        IArgumentMatcher easyMockMatcher = new EasyMock2Adapter(new BaseMatcher<Object>() {
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
        easyMockMatcher.appendTo(buffer);
        assertEquals("is like \"cheese\"", buffer.toString());
    }

}
