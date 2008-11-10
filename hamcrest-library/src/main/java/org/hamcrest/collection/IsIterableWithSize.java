package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.DiagnosingIterableMatcher;

public class IsIterableWithSize<E> extends DiagnosingIterableMatcher<E> {
    private final Matcher<? super Integer> sizeMatcher;

    public IsIterableWithSize(Matcher<? super Integer> sizeMatcher) {
        this.sizeMatcher = sizeMatcher;
    }

    @Override
    public boolean matchesSafely(Iterable<E> iterable, Description mismatchDescription) {
        Iterator<E> iterator = iterable.iterator();
        int size = 0;
        for (; iterator.hasNext(); iterator.next(), size++) {}
        if (!sizeMatcher.matches(size)) {
            mismatchDescription.appendText("The size did not match.");
            sizeMatcher.describeMismatch(size, mismatchDescription);
            return false;
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("an iterable with size ")
            .appendDescriptionOf(sizeMatcher);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsIterableWithSize<E>(sizeMatcher);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
        return iterableWithSize(equalTo(size));
    }
}
