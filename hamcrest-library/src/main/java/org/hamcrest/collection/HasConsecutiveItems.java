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
public class HasConsecutiveItems<T>
    extends TypeSafeDiagnosingMatcher<Collection<? extends T>> {
    private final List<Matcher<? super T>> matchers;

    public HasConsecutiveItems(List<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(
            Collection<? extends T> itemsToMatch,
            Description mismatchDescription) {
        List<? extends T> itemsToMatchList = new ArrayList<T>(itemsToMatch);

        for (int i = 0; i <= itemsToMatchList.size() - matchers.size(); i++) {
            boolean allMatchersMatched = true;
            for (int j = 0; j < matchers.size(); j++) {
                Matcher<? super T> matcher = matchers.get(j);
                if (!matcher.matches(itemsToMatchList.get(i + j))) {
                    allMatchersMatched = false;
                    break;
                }
            }
            if (allMatchersMatched) {
                return true;
            }
        }

        mismatchDescription
                .appendText("could not find items inside collection ")
                .appendValueList("[", ", ", "]", itemsToMatch);
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("collection contains consecutive items matching ")
                .appendList("[", ", ", "]", matchers);
    }

    @Factory
    public static <T> Matcher<Collection<? extends T>> hasConsecutiveItems(
            List<Matcher<? super T>> matchers) {
        return new HasConsecutiveItems<T>(matchers);
    }

    @Factory
    @SafeVarargs
    public static <T> Matcher<Collection<? extends T>> hasConsecutiveItems(
            Matcher<? super T>... matchers) {
        return hasConsecutiveItems(Arrays.asList(matchers));
    }

    @Factory
    public static <T> Matcher<Collection<? extends T>> hasConsecutiveItems(
            Iterable<? extends T> items) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        for (Object item : items) {
            matchers.add(equalTo(item));
        }
        return new HasConsecutiveItems<T>(matchers);
    }

    @Factory
    @SafeVarargs
    public static <T> Matcher<Collection<? extends T>> hasConsecutiveItems(T... elements) {
        return hasConsecutiveItems(Arrays.asList(elements));
    }
}
