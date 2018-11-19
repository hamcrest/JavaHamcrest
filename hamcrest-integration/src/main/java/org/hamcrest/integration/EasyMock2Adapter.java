package org.hamcrest.integration;

import org.easymock.IArgumentMatcher;
import org.easymock.EasyMock;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * An adapter allowing a Hamcrest {@link org.hamcrest.Matcher}
 * to act as an EasyMock {@link org.easymock.IArgumentMatcher}.
 *
 * @author Joe Walnes
 */
public class EasyMock2Adapter implements IArgumentMatcher {

    /**
     * Convenience factory method that will adapt a
     * Hamcrest {@link org.hamcrest.Matcher} to act as an
     * EasyMock {@link org.easymock.IArgumentMatcher} and
     * report it to EasyMock so it can be kept track of.
     */
    public static IArgumentMatcher adapt(Matcher<?> matcher) {
        EasyMock2Adapter easyMock2Matcher = new EasyMock2Adapter(matcher);
        EasyMock.reportMatcher(easyMock2Matcher);
        return easyMock2Matcher;
    }

    private final Matcher<?> hamcrestMatcher;

    public EasyMock2Adapter(Matcher<?> matcher) {
        this.hamcrestMatcher = matcher;
    }

    @Override
    public boolean matches(Object argument) {
        return hamcrestMatcher.matches(argument);
    }

    @Override
    public void appendTo(StringBuffer buffer) {
        hamcrestMatcher.describeTo(new StringDescription(buffer));
    }
}
