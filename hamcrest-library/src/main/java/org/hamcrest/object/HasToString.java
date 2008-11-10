package org.hamcrest.object;

import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class HasToString<T> extends DiagnosingMatcher<T> {
    private final Matcher<? super String> toStringMatcher;

    public HasToString(Matcher<? super String> toStringMatcher) {
        this.toStringMatcher = toStringMatcher;
    }

    @Override
    public boolean matches(Object item, Description mismatchDescription) {
        if (item == null) {
            mismatchDescription.appendText("item was null");
            return false;
        }
        String toString = item.toString();
        if (!toStringMatcher.matches(toString)) {
            mismatchDescription.appendText("toString() ");
            toStringMatcher.describeMismatch(toString, mismatchDescription);
            return false;
        }
        return true;
    }

    public void describeTo(Description description) {
        description
            .appendText("asString(")
            .appendDescriptionOf(toStringMatcher)
            .appendText(")");
    }

    /**
     * Evaluates whether item.toString() satisfies a given matcher.
     */
    @Factory
    public static <T> Matcher<T> hasToString(Matcher<? super String> toStringMatcher) {
        return new HasToString<T>(toStringMatcher);
    }

    /**
     * This is a shortcut to the frequently used has_string(equalTo(x)).
     *
     * For example,  assertThat(hasToString(equal_to(x)))
     *          vs.  assertThat(hasToString(x))
     */
    @Factory
    public static <T> Matcher<T> hasToString(String expectedToString) {
        return new HasToString<T>(equalTo(expectedToString));
    }
}
