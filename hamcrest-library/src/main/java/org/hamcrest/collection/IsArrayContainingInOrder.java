package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsArrayContainingInOrder<E> extends TypeSafeMatcher<E[]> {
    private final Collection<Matcher<? super E>> matchers;
    private final IsIterableContainingInAnyOrder<E> iterableMatcher;

    public IsArrayContainingInOrder(Collection<Matcher<? super E>> matchers) {
        this.iterableMatcher = new IsIterableContainingInAnyOrder<E>(matchers);
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(E[] item) {
        return iterableMatcher.matches(Arrays.asList(item));
    }

    public void describeTo(Description description) {
        description.appendList("[", ", ", "]", matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(E first, E second) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(E first, E second, E third) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(E first, E second, E third, E forth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(E first, E second, E third, E forth, E fifth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(E first, E second, E third, E forth, E fifth, E sixth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        matchers.add(equalTo(sixth));
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... matchers) {
        return arrayContaining(Arrays.asList(matchers));
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(Collection<Matcher<? super E>> matchers) {
        return new IsArrayContainingInOrder<E>(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(Matcher<E> first, Matcher<? super E> second) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth, Matcher<? super E> fifth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        return arrayContaining(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth, Matcher<? super E> fifth, Matcher<? super E> sixth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        matchers.add(sixth);
        return arrayContaining(matchers);
    }
}
