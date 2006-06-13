package org.hamcrest.integration;

import org.easymock.IArgumentMatcher;
import org.easymock.EasyMock;
import org.hamcrest.internal.StringDescription;
import org.hamcrest.Matcher;

/**
 * An adapter to allowing a Hamcrest {@link org.hamcrest.Matcher}
 * to act as an EasyMock {@link org.easymock.IArgumentMatcher}.
 *
 * @author Joe Walnes
 */
public class EasyMockAdapter implements IArgumentMatcher {

    /**
     * Convenience factory method that will adapt a
     * Hamcrest {@link org.hamcrest.Matcher} to act as an
     * EasyMock {@link org.easymock.IArgumentMatcher} and
     * report it to EasyMock so it can be kept track of.
     */
    public static IArgumentMatcher adapt(Matcher matcher) {
        EasyMockAdapter easyMockMatcher = new EasyMockAdapter(matcher);
        EasyMock.reportMatcher(easyMockMatcher);
        return easyMockMatcher;
    }

    private final Matcher hamcrestMatcher;

    public EasyMockAdapter(Matcher matcher) {
        this.hamcrestMatcher = matcher;
    }

    @SuppressWarnings({"unchecked"})
    public boolean matches(Object argument) {
        return hamcrestMatcher.match(argument);
    }

    public void appendTo(StringBuffer buffer) {
        hamcrestMatcher.describeTo(new StringDescription(buffer));
    }

}
