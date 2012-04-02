package org.hamcrest.integration;

import junit.framework.TestCase;
import org.easymock.IArgumentMatcher;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import static org.hamcrest.core.IsEqual.equalTo;

public class EasyMock2AdapterTest extends TestCase {

    public static interface InterfaceToMock {
        void doStuff(String name, int number);
    }

    public void testAdaptsHamcrestMatcherToEasyMockArgumentsMatcher() {
        IArgumentMatcher easyMockMatcher = new EasyMock2Adapter(equalTo("expected"));
        assertTrue("Should have matched", easyMockMatcher.matches("expected"));
        assertFalse("Should not have matched", easyMockMatcher.matches("unexpected"));
    }

    public void testDelegatesDescriptionToUnderlyingMatcher() {
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
