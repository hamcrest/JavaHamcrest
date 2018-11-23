package org.hamcrest.integration;

import org.jmock.core.Constraint;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * An adapter allowing a Hamcrest {@link org.hamcrest.Matcher}
 * to act as an jMock1 {@link org.jmock.core.Constraint}.
 * Note, this is not necessary for jMock2 as it supports Hamcrest
 * out of the box.
 *
 * @author Joe Walnes
 */
public class JMock1Adapter implements Constraint {

    /**
     * Convenience factory method that will adapt a
     * Hamcrest {@link org.hamcrest.Matcher} to act as an
     * jMock {@link org.jmock.core.Constraint}.
     */
    public static Constraint adapt(Matcher<?> matcher) {
        return new JMock1Adapter(matcher);
    }

    private final Matcher<?> hamcrestMatcher;

    public JMock1Adapter(Matcher<?> matcher) {
        this.hamcrestMatcher = matcher;
    }

    @Override
    public boolean eval(Object o) {
        return hamcrestMatcher.matches(o);
    }

    @Override
    public StringBuffer describeTo(StringBuffer buffer) {
        hamcrestMatcher.describeTo(new StringDescription(buffer));
        return buffer;
    }
}
