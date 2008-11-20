package org.hamcrest.collection;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsMapContainingKey<K> extends TypeSafeMatcher<Map<? extends K,?>> {
    private final Matcher<? super K> keyMatcher;
    
    public IsMapContainingKey(Matcher<? super K> keyMatcher) {
        this.keyMatcher = keyMatcher;
    }
    
    @Override
    public boolean matchesSafely(Map<? extends K, ? > item) {
        for (K key : item.keySet()) {
            if (keyMatcher.matches(key)) {
                return true;
            }
        }
        return false;
    }

    public void describeTo(Description description) {
        description.appendText("map with key ")
                   .appendDescriptionOf(keyMatcher);
    }

    @Factory
    public static <K> Matcher<Map<? extends K,?>> hasKey(K key) {
        return IsMapContainingKey.<K>hasKey(equalTo(key));
    }
    
    @Factory
    public static <K> Matcher<Map<? extends K,?>> hasKey(Matcher<? super K> keyMatcher) {
        return new IsMapContainingKey<K>(keyMatcher);
    }
}
