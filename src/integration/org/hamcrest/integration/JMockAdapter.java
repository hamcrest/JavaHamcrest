package org.hamcrest.integration;

import org.jmock.core.Constraint;
import org.hamcrest.Matcher;
import org.hamcrest.internal.StringDescription;

/**
 * An adapter to allowing a Hamcrest {@link org.hamcrest.Matcher}
 * to act as an jMock {@link org.jmock.core.Constraint}.
 *
 * @author Joe Walnes
 */
public class JMockAdapter implements Constraint {

    /**
     * Convenience factory method that will adapt a
     * Hamcrest {@link org.hamcrest.Matcher} to act as an
     * jMock {@link org.jmock.core.Constraint}.
     */
    public static Constraint adapt(Matcher matcher) {
        return new JMockAdapter(matcher);
    }

    private final Matcher hamcrestMatcher;

    public JMockAdapter(Matcher matcher) {
        this.hamcrestMatcher = matcher;
    }

    @SuppressWarnings({"unchecked"})
    public boolean eval(Object o) {
        return hamcrestMatcher.match(o);
    }

    public StringBuffer describeTo(StringBuffer buffer) {
        hamcrestMatcher.describeTo(new StringDescription(buffer));
        return buffer;
    }

}
