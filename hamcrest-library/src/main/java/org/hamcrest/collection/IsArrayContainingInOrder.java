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
    private final IsIterableContainingInOrder<E> iterableMatcher;

    public IsArrayContainingInOrder(List<Matcher<? super E>> matchers) {
        this.iterableMatcher = new IsIterableContainingInOrder<E>(matchers);
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(E[] item) {
        return iterableMatcher.matches(Arrays.asList(item));
    }
    
    @Override
    public void describeMismatchSafely(E[] item, Description mismatchDescription) {
      iterableMatcher.describeMismatch(Arrays.asList(item), mismatchDescription);
    };

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
    public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... matchers) {
        return arrayContaining(Arrays.asList(matchers));
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> matchers) {
        return new IsArrayContainingInOrder<E>(matchers);
    }
}
