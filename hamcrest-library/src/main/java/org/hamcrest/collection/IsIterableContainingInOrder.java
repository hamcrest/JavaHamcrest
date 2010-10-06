package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingInOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInOrder(List<Matcher<? super E>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> iterable, Description mismatchDescription) {
        MatchSeries<E> matchSeries = new MatchSeries<E>(matchers, mismatchDescription);
        for (E item : iterable) {
            if (!matchSeries.matches(item)) {
                return false;
            }
        }

        return matchSeries.isFinished();
    }

    public void describeTo(Description description) {
        description.appendText("iterable containing ").appendList("[", ", ", "]", matchers);
    }

    private static class MatchSeries<F> {
        public final List<Matcher<? super F>> matchers;
        private final Description mismatchDescription;
        public int nextMatchIx = 0;

        public MatchSeries(List<Matcher<? super F>> matchers, Description mismatchDescription) {
            this.mismatchDescription = mismatchDescription;
            if (matchers.isEmpty()) {
                throw new IllegalArgumentException("Should specify at least one expected element");
            }
            this.matchers = matchers;
        }

        public boolean matches(F item) {
            return isNotSurplus(item) && isMatched(item);
        }

        public boolean isFinished() {
            if (nextMatchIx < matchers.size()) {
                mismatchDescription.appendText("No item matched: ").appendDescriptionOf(matchers.get(nextMatchIx));
                return false;
            }
            return true;
        }

        private boolean isMatched(F item) {
            Matcher<? super F> matcher = matchers.get(nextMatchIx);
            if (!matcher.matches(item)) {
                describeMismatch(matcher, item);
                return false;
            }
            nextMatchIx++;
            return true;
        }

        private boolean isNotSurplus(F item) {
            if (matchers.size() <= nextMatchIx) {
                mismatchDescription.appendText("Not matched: ").appendValue(item);
                return false;
            }
            return true;
        }

        private void describeMismatch(Matcher<? super F> matcher, F item) {
            mismatchDescription.appendText("item " + nextMatchIx + ": ");
            matcher.describeMismatch(item, mismatchDescription);
        }
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }

        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(final Matcher<? super E> item) {
        return contains(new ArrayList<Matcher<? super E>>(Arrays.asList(item)));
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... matchers) {
        List<Matcher<? super E>> matcherList = Arrays.asList(matchers);
        return contains(matcherList);
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> contents) {
        return new IsIterableContainingInOrder<E>(contents);
    }
}
