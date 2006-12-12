package org.hamcrest.collection;

import static org.hamcrest.core.AllOf.allOf;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.eq;

import java.util.Collection;
import java.util.ArrayList;

public class IsCollectionContaining<T> extends TypeSafeMatcher<Collection<T>> {
    private final Matcher<T> elementMatcher;

    public IsCollectionContaining(Matcher<T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    public boolean matchesSafely(Collection<T> collection) {
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
    public static <T> Matcher<Collection<T>> collectionContaining(Matcher<T> elementMatcher) {
        return new IsCollectionContaining<T>(elementMatcher);
    }

    @Factory
    public static <T> Matcher<Collection<T>> collectionContaining(T element) {
        return collectionContaining(eq(element));
    }

    @Factory
    public static <T> Matcher<Collection<T>> collectionContainingAllOf(Matcher<T>... elementMatchers) {
        Collection<Matcher<Collection<T>>> all = new ArrayList<Matcher<Collection<T>>>(elementMatchers.length);
        for (Matcher<T> elementMatcher : elementMatchers) {
            all.add(collectionContaining(elementMatcher));
        }
        return allOf(all);
    }

}
