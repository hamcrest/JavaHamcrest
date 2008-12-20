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
import org.hamcrest.TypeSafeMatcher;

public class IsIterableContainingInOrder<E> extends TypeSafeMatcher<Iterable<E>> {
    private final Collection<Matcher<? super E>> matchers;

    public IsIterableContainingInOrder(Collection<Matcher<? super E>> contents) {
        if (contents.isEmpty()) {
            throw new IllegalArgumentException("Should specify at least one expected element");
        }
        this.matchers = contents;
    }

    @Override
    public boolean matchesSafely(Iterable<E> iterable) {
        Iterator<E> items = iterable.iterator();
        Iterator<Matcher<? super E>> matchersIterator = matchers.iterator();
        while (items.hasNext() && matchersIterator.hasNext()) {
            if (!matchersIterator.next().matches(items.next())) {
                return false;
            }
        }
        return !items.hasNext() && !matchersIterator.hasNext();
    }

    @Override
    public void describeMismatchSafely(Iterable<E> actual, Description mismatchDescription) {
      mismatchDescription.appendText("iterable was ").appendValueList("[", ", ", "]", actual);
    }

    public void describeTo(Description description) {
        description.appendText("iterable over ")
            .appendList("[", ", ", "]", matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second, E third) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second, E third, E forth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second, E third, E forth, E fifth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second, E third, E forth, E fifth, E sixth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        matchers.add(equalTo(sixth));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<? super E>... items) {
        return contains(Arrays.asList(items));
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth, Matcher<? super E> fifth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth, Matcher<? super E> fifth, Matcher<? super E> sixth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        matchers.add(sixth);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Collection<Matcher<? super E>> contents) {
        return new IsIterableContainingInOrder<E>(contents);
    }
}
