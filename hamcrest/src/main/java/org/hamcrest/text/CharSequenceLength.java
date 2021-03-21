package org.hamcrest.text;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Marco Leichsenring
 * @author Steve Freeman
 */
public class CharSequenceLength extends FeatureMatcher<CharSequence, Integer> {

    /**
     * @param lengthMatcher         The matcher to apply to the feature
     */
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
      */
     public static Matcher<CharSequence> hasLength(Matcher<? super Integer> lengthMatcher) {
         return new CharSequenceLength(lengthMatcher);
     }
}


