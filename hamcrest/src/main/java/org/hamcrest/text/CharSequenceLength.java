package org.hamcrest.text;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * A Matcher that checks the length of a string.
 * @author Marco Leichsenring
 * @author Steve Freeman
 */
public class CharSequenceLength extends FeatureMatcher<CharSequence, Integer> {


    /**
     * Constructor, best called from {@link #hasLength(Matcher)}.
     * @see #hasLength(Matcher)
     */
    @SuppressWarnings({"WeakerAccess", "doclint"})
    public CharSequenceLength(Matcher<? super Integer> lengthMatcher) {
        super(lengthMatcher, "a CharSequence with length", "length");
    }

    @Override
    protected Integer featureValueOf(CharSequence actual) {
        return actual.length();
    }

    /**
     * Creates a matcher of {@link CharSequence} that matches when a char sequence has the given length
     * For example:
     *
     * <pre>
     * assertThat("text", hasLength(4))
     * </pre>
     *
     * @param length the expected length of the string
     * @return The matcher.
     */
    public static Matcher<CharSequence> hasLength(int length) {
        return new CharSequenceLength(equalTo(length));
    }

    /**
      * Creates a matcher of {@link CharSequence} that matches when a char sequence has the given length
      * For example:
      *
      * <pre>
      * assertThat("text", hasLength(lessThan(4)))
      * </pre>
      *
      * @param lengthMatcher the expected length of the string
      * @return The matcher.
      */
     @SuppressWarnings("WeakerAccess")
     public static Matcher<CharSequence> hasLength(Matcher<? super Integer> lengthMatcher) {
         return new CharSequenceLength(lengthMatcher);
     }

}
