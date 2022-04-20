package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

import static java.lang.Math.abs;

/**
 * Is the value a number equal to a value within some range of
 * acceptable error?
 */
public abstract class BaseCloseTo<N extends Comparable<N>> extends TypeSafeMatcher<N> {

    protected final N delta;
    protected final N value;

    public BaseCloseTo(N value, N error) {
        this.delta = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(N item) {
        return actualDelta(item).compareTo(delta) <= 0;
    }

    @Override
    public void describeMismatchSafely(N item, Description mismatchDescription) {
        mismatchDescription.appendValue(item)
            .appendText(" differed from ")
            .appendValue(value)
            .appendText(" by ")
            .appendValue(actualDelta(item))
            .appendText(" but allowed delta is less than ")
            .appendValue(delta);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
            .appendValue(delta)
            .appendText(" of ")
            .appendValue(value);
    }

    protected abstract N actualDelta(N item);
}
