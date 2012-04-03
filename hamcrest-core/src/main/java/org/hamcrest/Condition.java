package org.hamcrest;

/**
 * A Condition implements part of a multi-step match. We sometimes need to write matchers
 * that have a sequence of steps, where each step depends on the result of the previous
 * step and we can stop processing as soon as a step fails. These classes provide
 * infrastructure for writing such a sequence.
 *
 * Based on https://github.com/npryce/maybe-java
 * @author Steve Freeman 2012 http://www.hamcrest.com
 */

public abstract class Condition<T> {
    public static final NotMatched<Object> NOT_MATCHED = new NotMatched<Object>();

    public interface Step<I, O> {
        Condition<O> apply(I value, Description mismatch);
    }

    public boolean matching(Matcher<T> match) { return matching(match, ""); }
    public abstract boolean matching(Matcher<T> match, String message);
    public abstract <U> Condition<U> and(Step<? super T, U> mapping);
    public final <U> Condition<U> then(Step<? super T, U> mapping) { return and(mapping); }

    @SuppressWarnings("unchecked")
    public static <T> Condition<T> notMatched() {
        return (Condition<T>) NOT_MATCHED;
    }

    public static <T> Condition<T> matched(final T theValue, final Description mismatch) {
        return new Condition<T>() {
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
        };
    }

    private static class NotMatched<T> extends Condition<T> {
        @Override public boolean matching(Matcher<T> match, String message) { return false; }

        @Override public <U> Condition<U> and(Step<? super T, U> mapping) {
            return notMatched();
        }
    }
}
