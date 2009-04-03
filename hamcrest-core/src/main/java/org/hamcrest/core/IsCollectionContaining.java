package org.hamcrest.core;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsCollectionContaining<T> extends TypeSafeDiagnosingMatcher<Iterable<? super T>> {
    private final Matcher<? super T> elementMatcher;

    public IsCollectionContaining(Matcher<? super T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    @Override
    protected boolean matchesSafely(Iterable<? super T> collection, Description mismatchDescription) {
        boolean isPastFirst = false;
        for (Object item : collection) {
            if (elementMatcher.matches(item)){
                return true;
            }
            if (isPastFirst) {
              mismatchDescription.appendText(", ");
            }
            elementMatcher.describeMismatch(item, mismatchDescription);
            isPastFirst = true;
        }
        return false;
    }

    public void describeTo(Description description) {
        description
            .appendText("a collection containing ")
            .appendDescriptionOf(elementMatcher);
    }

    @Factory
    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> elementMatcher) {
      return new IsCollectionContaining<T>(elementMatcher);
    }

    @Factory
    public static <T> Matcher<Iterable<? super T>> hasItem(T element) {
      // Doesn't forward to hasItem() method so compiler can sort out generics.
      return new IsCollectionContaining<T>(equalTo(element));
    }

    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... elementMatchers) {
        List<Matcher<? super Iterable<T>>> all = new ArrayList<Matcher<? super Iterable<T>>>(elementMatchers.length);
        
        for (Matcher<? super T> elementMatcher : elementMatchers) {
          // Doesn't forward to hasItem() method so compiler can sort out generics.
          all.add(new IsCollectionContaining<T>(elementMatcher));
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
