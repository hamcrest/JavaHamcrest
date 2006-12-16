package org.hamcrest.collection;

import static org.hamcrest.core.AllOf.allOf;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Collection;
import java.util.ArrayList;

public class IsCollectionContaining<T> extends TypeSafeMatcher<Iterable<T>> {
    private final Matcher<T> elementMatcher;

    public IsCollectionContaining(Matcher<T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    public boolean matchesSafely(Iterable<T> collection) {
        for (T item : collection) {
            if (elementMatcher.matches(item)){
                return true;
            }
        }
        return false;
    }

    public void describeTo(Description description) {
        description.appendText("a collection containing ");
        elementMatcher.describeTo(description);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> hasItem(Matcher<T> elementMatcher) {
        return new IsCollectionContaining<T>(elementMatcher);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> hasItem(T element) {
        return hasItem(equalTo(element));
    }

    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<T>... elementMatchers) {
        Collection<Matcher<Iterable<T>>> all = new ArrayList<Matcher<Iterable<T>>>(elementMatchers.length);
        for (Matcher<T> elementMatcher : elementMatchers) {
            all.add(hasItem(elementMatcher));
        }
        return allOf(all);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(T... elements) {
        Collection<Matcher<Iterable<T>>> all = new ArrayList<Matcher<Iterable<T>>>(elements.length);
        for (T element : elements) {
            all.add(hasItem(element));
        }
        return allOf(all);
    }

}
