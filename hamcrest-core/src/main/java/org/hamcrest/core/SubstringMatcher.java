package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class SubstringMatcher extends TypeSafeMatcher<CharSequence> {

    protected final String substring;

    private final String relationshipDesc;

    private final boolean ignoringCase;

    /**
     * Provides common instance initialization logic for sub-classes to support
     * common behaviour.
     *
     * @param relationshipDesc sets information for assembling of mismatch
     *                         description e.g. "ending with", "starting with"
     *                         etc.
     * @param ignoringCase     sets the flag specifies if the matcher has to
     *                         take into consideration parameters case or not
     * @param substring        sets the substring for searching
     */
    protected SubstringMatcher(String relationshipDesc,
                               boolean ignoringCase,
                               CharSequence substring) {
        this.relationshipDesc = relationshipDesc;
        this.ignoringCase = ignoringCase;
        if (null == substring) {
            throw new IllegalArgumentException("missing substring");
        }
        this.substring = substring.toString();
    }

    /**
     * Service method. Performs evaluation for the provided substring according
     * to matcher logic.
     *
     * @param string the string for evaluation to set
     * @return {@code true} if the evaluation result is positive
     * or {@code false} if not
     */
    protected abstract boolean evalSubstringOf(String string);

    @Override
    public boolean matchesSafely(CharSequence item) {
        return this.evalSubstringOf(item.toString());
    }

    @Override
    public void describeMismatchSafely(CharSequence item,
                                       Description mismatchDescription) {
        mismatchDescription
                .appendText("was \"")
                .appendText(item.toString())
                .appendText("\"");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string ")
                .appendText(this.relationshipDesc)
                .appendText(" ")
                .appendValue(this.substring);
        if (this.ignoringCase) {
            description.appendText(" ignoring case");
        }
    }

    /**
     * Converts the provided argument according to value of
     * {@link #ignoringCase} instance variable or returns the provided value.
     *
     * @param arg the argument for conversion to set
     * @return the provided argument if the {@link #ignoringCase} is
     * {@code false} or converted to lowercase argument if the
     * {@link #ignoringCase} is {@code true}
     */
    protected String converted(String arg) {
        return this.ignoringCase ? arg.toLowerCase() : arg;
    }
}
