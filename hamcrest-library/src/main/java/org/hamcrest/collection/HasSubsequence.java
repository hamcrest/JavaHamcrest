package org.hamcrest.collection;

import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * Matches if a collection contains another collection's items in
 * consecutive order anywhere inside it.
 */
public class HasSubsequence<T>
    extends TypeSafeDiagnosingMatcher<Collection<? extends T>> {
    private final List<Matcher<? super T>> matchers;

    public HasSubsequence(List<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(
            Collection<? extends T> subsequenceToMatch,
            Description mismatchDescription) {
        List<? extends T> subsequenceToMatchList = new ArrayList<T>(subsequenceToMatch);

        for (int i = 0; i <= subsequenceToMatchList.size() - matchers.size(); i++) {
            boolean allMatchersMatched = true;
            for (int j = 0; j < matchers.size(); j++) {
                Matcher<? super T> matcher = matchers.get(j);
                if (!matcher.matches(subsequenceToMatchList.get(i + j))) {
                    allMatchersMatched = false;
                    break;
                }
            }
            if (allMatchersMatched) {
                return true;
            }
        }

        mismatchDescription
                .appendText("could not find subsequence inside collection ")
                .appendValueList("[", ", ", "]", subsequenceToMatch);
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("collection contains subsequence matching ")
                .appendList("[", ", ", "]", matchers);
    }

    @Factory
    public static <T> Matcher<Collection<? extends T>> hasSubsequence(
            List<Matcher<? super T>> matchers) {
        return new HasSubsequence<T>(matchers);
    }

    @Factory
    public static <T> Matcher<Collection<? extends T>> hasSubsequence(
            Matcher<? super T>... matchers) {
        return hasSubsequence(Arrays.asList(matchers));
    }

    @Factory
    public static <T> Matcher<Collection<? extends T>> hasSubsequence(
            Iterable<? extends T> items) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        for (Object item : items) {
            matchers.add(equalTo(item));
        }
        return new HasSubsequence<T>(matchers);
    }

    @Factory
    public static <T> Matcher<Collection<? extends T>> hasSubsequence(T... elements) {
        return hasSubsequence(Arrays.asList(elements));
    }
}
