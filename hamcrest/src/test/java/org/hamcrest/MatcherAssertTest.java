package org.hamcrest;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public final class MatcherAssertTest {

    @Test public void
    includesDescriptionOfTestedValueInErrorMessage() {
        String expected = "expected";
        String actual = "actual";
        String endLine = System.lineSeparator();

        String expectedMessage = "identifier" + endLine + "Expected: \"expected\"" + endLine + "     but: was \"actual\"";

        try {
            assertThat("identifier", actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    @Test public void
    descriptionCanBeElided() {
        String expected = "expected";
        String actual = "actual";
        String endLine = System.lineSeparator();

        String expectedMessage = endLine + "Expected: \"expected\"" + endLine + "     but: was \"actual\"";

        try {
            assertThat(actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    @Test public void
    canTestBooleanDirectly() {
        assertThat("success reason message", true);

        try {
            assertThat("failing reason message", false);
        }
        catch (AssertionError e) {
            assertEquals("failing reason message", e.getMessage());
            return;
        }

        fail("should have failed");
    }

    @Test public void
    includesMismatchDescription() {
        Matcher<String> matcherWithCustomMismatchDescription = new BaseMatcher<String>() {
            @Override
            public boolean matches(Object item) {
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Something cool");
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {
                mismatchDescription.appendText("Not cool");
            }
        };

        String endLine = System.lineSeparator();
        String expectedMessage = endLine + "Expected: Something cool" + endLine + "     but: Not cool";

        try {
            assertThat("Value", matcherWithCustomMismatchDescription);
            fail("should have failed");
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test public void
    canAssertSubtypes() {
        assertThat(1, equalTo((Number) 1));
    }

    @Test public void
    throwableIsOfMatchingInstance() {
        assertThat(
                new Executable() {
                    @Override
                    public void execute() {
                        throw new IllegalStateException();
                    }
                },
                throwsInstanceOf(IllegalStateException.class)
        );
    }

    @Test public void
    throwableIsNotOfMatchingInstance() {
        String endLine = System.lineSeparator();
        String expectedMessage = endLine + "Expected: throws an instance of java.io.IOException" + endLine
                + "     but: threw but <java.lang.IllegalStateException> is a java.lang.IllegalStateException";
        try {
            assertThat(
                    new Executable() {
                        @Override
                        public void execute() {
                            throw new IllegalStateException();
                        }
                    },
                    throwsInstanceOf(IOException.class)
            );
            fail("should have failed");
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test public void
    throwableHasMatchingMessage() {
        assertThat(
                new Executable() {
                    @Override
                    public void execute() throws Exception {
                        throw new Exception("message");
                    }
                },
                doesThrow(withMessage(equalTo("message")))
        );
    }

    @Test public void
    throwableDoesNotHaveMatchingMessage() {
        String endLine = System.lineSeparator();
        String expectedMessage = endLine + "Expected: throws with message \"expected message\"" + endLine
                + "     but: threw but message was \"actual message\"";
        try {
            assertThat(
                    new Executable() {
                        @Override
                        public void execute() throws Exception {
                            throw new Exception("actual message");
                        }
                    },
                    doesThrow(withMessage("expected message"))
            );
            fail("should have failed");
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test public void
    throwableExecutionDoesNotThrow() {
        String endLine = System.lineSeparator();
        String expectedMessage = endLine + "Expected: throws an instance of java.lang.NoSuchMethodError"
                + endLine + "     but: did not throw";
        try {
            assertThat(
                    new Executable() {
                        @Override
                        public void execute() {
                            // Do nothing
                        }
                    },
                    throwsInstanceOf(NoSuchMethodError.class)
            );
            fail("should have failed");
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test public void
    throwableCauseMatches() {
        Matcher<? extends Throwable> instanceOfMatcher = instanceOf(XMLStreamException.class);
        assertThat(
                new Executable() {
                    @Override
                    public void execute() {
                        throw new RuntimeException(new XMLStreamException());
                    }
                },
                doesThrow(becauseOf(instanceOfMatcher))
        );
    }

    @Test public void
    throwableCauseDoesNotMatch() {
        String endLine = System.lineSeparator();
        String expectedMessage = endLine + "Expected: throws because of an instance of java.lang.NullPointerException"
                + endLine + "     but: threw but cause <java.lang.IllegalArgumentException> is a java.lang.IllegalArgumentException";
        try {
            Matcher<? extends Throwable> instanceOfMatcher = instanceOf(NullPointerException.class);
            assertThat(
                    new Executable() {
                        @Override
                        public void execute() {
                            throw new RuntimeException(new IllegalArgumentException());
                        }
                    },
                    doesThrow(becauseOf(instanceOfMatcher))
            );
            fail("should have failed");
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test public void
    throwableExecutionDoesNotMatchWithCustomMessage() {
        String endLine = System.lineSeparator();
        String expectedMessage = "Custom message"
                + endLine + "Expected: throws an instance of java.lang.NullPointerException"
                + endLine + "     but: threw but <java.lang.IllegalArgumentException> is a java.lang.IllegalArgumentException";
        try {
            assertThat(
                    "Custom message",
                    new Executable() {
                        @Override
                        public void execute() {
                            throw new IllegalArgumentException();
                        }
                    },
                    throwsInstanceOf(NullPointerException.class)
            );
            fail("should have failed");
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
