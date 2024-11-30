package org.hamcrest;

/**
 * A Condition implements part of a multi-step match. We sometimes need to write matchers
 * that have a sequence of steps, where each step depends on the result of the previous
 * step, and we can stop processing as soon as a step fails. These classes provide
 * infrastructure for writing such a sequence.
 *
 * <p>Based on Nat Pryce's <a href="https://github.com/npryce/maybe-java">maybe-java</a>.
 * </p>
 *
 * @param <T> the matched value type
 * @author Steve Freeman 2012 http://www.hamcrest.com
 */
public abstract class Condition<T> {

    /**
     * Represents a single step in a multi-step sequence
     * @param <I> the initial value type
     * @param <O> the next step value type
     */
    @FunctionalInterface
    public interface Step<I, O> {
        /**
         * Apply this condition to a value
         * @param value the value to match
         * @param mismatch the description for mismatches
         * @return the next condition
         */
        Condition<O> apply(I value, Description mismatch);
    }

    private Condition() { }

    /**
     * Applies the matcher as the final step in the sequence
     * @param match the value matcher
     * @param message a description of the value
     * @return true if the matcher matches the value, otherwise false
     */
    public abstract boolean matching(Matcher<T> match, String message);

    /**
     * Applies the matcher as the final step in the sequence
     * @param match the value matcher
     * @return true if the matcher matches the value, otherwise false
     */
    public final boolean matching(Matcher<T> match) { return matching(match, ""); }

    /**
     * Applies the mapping to the current value in the sequence
     * @param mapping the current step in the sequence
     * @return the condition for the next step in the sequence
     * @param <U> the type of the next value
     */
    public abstract <U> Condition<U> and(Step<? super T, U> mapping);

    /**
     * An alias for {@link #and(Step)}, which applies the mapping to the current value in the
     * sequence.
     * @param mapping the current step in the sequence
     * @return the condition for the next step in the sequence
     * @param <U> the type of the next value
     */
    public final <U> Condition<U> then(Step<? super T, U> mapping) { return and(mapping); }

    /**
     * Called by steps when a mismatch occurs.
     * @return a condition in the not matched state
     * @param <T> the type of the unmatched value
     */
    @SuppressWarnings("unchecked")
    public static <T> Condition<T> notMatched() {
        return (Condition<T>) NotMatched.NOT_MATCHED;
    }

    /**
     * Called by steps when a match occurs
     * @param theValue the value that was matched
     * @param mismatch a description for potential future mismatches
     * @return the condition in a matched state
     * @param <T> the type of the matched value
     */
    public static <T> Condition<T> matched(final T theValue, final Description mismatch) {
        return new Matched<>(theValue, mismatch);
    }

    private static final class Matched<T> extends Condition<T> {
        private final T theValue;
        private final Description mismatch;

        private Matched(T theValue, Description mismatch) {
            this.theValue = theValue;
            this.mismatch = mismatch;
        }

        @Override
        public boolean matching(Matcher<T> matcher, String message) {
            if (matcher.matches(theValue)) {
                return true;
            }
            mismatch.appendText(message);
            matcher.describeMismatch(theValue, mismatch);
            return false;
        }

        @Override
        public <U> Condition<U> and(Step<? super T, U> next) {
            return next.apply(theValue, mismatch);
        }
    }

    private static final class NotMatched<T> extends Condition<T> {
        public static final NotMatched<Object> NOT_MATCHED = new NotMatched<>();

        @Override public boolean matching(Matcher<T> match, String message) { return false; }

        @Override public <U> Condition<U> and(Step<? super T, U> mapping) {
            return notMatched();
        }
    }

}
