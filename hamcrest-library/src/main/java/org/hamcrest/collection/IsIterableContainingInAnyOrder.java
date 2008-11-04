package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.DiagnosingIterableMatcher;

public class IsIterableContainingInAnyOrder<T> extends DiagnosingIterableMatcher<T> {
    private final Collection<Matcher<? super T>> matchers;

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> matchers) {
        if (matchers.size() < 2) {
            throw new IllegalArgumentException("Must specify at least two expected elements!");
        }
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(Iterable<T> iterable, Description mismatchDescription) {
        Iterator<T> items = iterable.iterator();
        List<Matcher<? super T>> currentMatchers = copyOfMatchers();
        while (items.hasNext() && !currentMatchers.isEmpty()) {
            T item = items.next();
            Iterator<Matcher<? super T>> availableMatchers = currentMatchers.iterator();
            while (availableMatchers.hasNext()) {
                Matcher<? super T> matcher = availableMatchers.next();
                if (matcher.matches(item)) {
                    availableMatchers.remove();
                    break;
                }
            }
        }
        boolean result = true;
        if (!currentMatchers.isEmpty()) {
            mismatchDescription.appendList("The following matchers did not match an element: ", ", ", ".", currentMatchers);
            result = false;
        }
        if (items.hasNext()) {
            mismatchDescription.appendText("The following items did not match any matcher: ");
            mismatchDescription.appendValue(items.next());
            while (items.hasNext()) {
                mismatchDescription.appendText(", ");
                mismatchDescription.appendValue(items.next());
            }
            result = false;
        }
        return result;
    }

    private List<Matcher<? super T>> copyOfMatchers() {
        List<Matcher<? super T>> availableMatchersForIteration = new ArrayList<Matcher<? super T>>();
        for (Matcher<? super T> matcher : matchers) {
            availableMatchersForIteration.add(matcher);
        }
        return availableMatchersForIteration;
    }

    public void describeTo(Description description) {
        description.appendText("iterable over ")
            .appendList("[", ", ", "]", matchers)
            .appendText(" in any order");
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<? super T>... matchers) {
        return containsInAnyOrder(Arrays.asList(matchers));
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T... items) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        for (T item : items) {
            matchers.add(equalTo(item));
        }
        return new IsIterableContainingInAnyOrder<T>(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second, T third) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second, T third, T forth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second, T third, T forth, T fifth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second, T third, T forth, T fifth, T sixth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        matchers.add(equalTo(sixth));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> forth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> forth, Matcher<? super T> fifth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> forth, Matcher<? super T> fifth,  Matcher<? super T> sixth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        matchers.add(sixth);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Collection<Matcher<? super T>> matchers) {
        return new IsIterableContainingInAnyOrder<T>(matchers);
    }
}
