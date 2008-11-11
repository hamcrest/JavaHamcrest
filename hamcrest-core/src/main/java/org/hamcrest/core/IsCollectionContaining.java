package org.hamcrest.core;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCollectionContaining<T> extends TypeSafeMatcher<Iterable<T>> {
    private final Matcher<? super T> elementMatcher;

    public IsCollectionContaining(Matcher<? super T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    @Override
    public boolean matchesSafely(Iterable<T> collection) {
        for (T item : collection) {
            if (elementMatcher.matches(item)){
                return true;
            }
        }
        return false;
    }

    public void describeTo(Description description) {
        description
            .appendText("a collection containing ")
            .appendDescriptionOf(elementMatcher);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> hasItem(Matcher<? super T> elementMatcher) {
        return new IsCollectionContaining<T>(elementMatcher);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> hasItem(T element) {
        return hasItem(equalTo(element));
    }

    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... elementMatchers) {
        List<Matcher<? super Iterable<T>>> all = new ArrayList<Matcher<? super Iterable<T>>>(elementMatchers.length);
        
        for (Matcher<? super T> elementMatcher : elementMatchers) {
            Matcher<? super Iterable<T>> itemMatcher = hasItem(elementMatcher);
            all.add(itemMatcher);
        }
        
        return allOf(all);
    }
    
    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(T... elements) {
        List<Matcher<? super Iterable<T>>> all = new ArrayList<Matcher<? super Iterable<T>>>(elements.length);
        
        for (T element : elements) {
            all.add(hasItem(element));
        }
        
        return allOf(all);
    }
}
