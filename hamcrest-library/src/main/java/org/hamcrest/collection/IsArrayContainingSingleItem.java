package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsArrayContainingSingleItem<E> extends TypeSafeMatcher<E[]> {
    private final Matcher<? super E> itemMatcher;
    private final IsIterableContainingSingleItem<E> iterableMatcher;
    
    public IsArrayContainingSingleItem(Matcher<? super E> itemMatcher) {
        this.itemMatcher = itemMatcher;
        this.iterableMatcher = new IsIterableContainingSingleItem<E>(itemMatcher);
    }
    
    @Override
    public boolean matchesSafely(E[] item) {
        return iterableMatcher.matches(Arrays.asList(item));
    }

    public void describeTo(Description description) {
        description.appendText("a singleton array with [")
            .appendDescriptionOf(itemMatcher)
            .appendText("]");
    }
    
    @Factory
    public static <E> Matcher<E[]> arrayWithSingleItem(Matcher<? super E> itemMatcher) {
        return new IsArrayContainingSingleItem<E>(itemMatcher);
    }
    
    @Factory
    public static <E> Matcher<E[]> arrayWithSingleItem(E item) {
        return arrayWithSingleItem(equalTo(item));
    }   
}
