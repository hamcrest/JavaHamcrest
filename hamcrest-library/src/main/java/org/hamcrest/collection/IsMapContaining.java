package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;
import java.util.Map.Entry;

import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsMapContaining<K,V> extends TypeSafeMatcher<Map<? extends K, ? extends V>> {
    private final Matcher<? super K> keyMatcher;
    private final Matcher<? super V> valueMatcher;

    public IsMapContaining(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        this.keyMatcher = keyMatcher;
        this.valueMatcher = valueMatcher;
    }

    @Override
    public boolean matchesSafely(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            if (keyMatcher.matches(entry.getKey()) && valueMatcher.matches(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeMismatchSafely(Map<? extends K, ? extends V> map, Description mismatchDescription) {
      mismatchDescription.appendText("map was ").appendValueList("[", ", ", "]", map.entrySet());
    }

    public void describeTo(Description description) {
        description.appendText("map containing [")
                   .appendDescriptionOf(keyMatcher)
                   .appendText("->")
                   .appendDescriptionOf(valueMatcher)
                   .appendText("]");
    }

    @Factory
    public static <K,V> Matcher<Map<? extends K,? extends V>> hasEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return new IsMapContaining<K,V>(keyMatcher, valueMatcher);
    }

    @Factory
    public static <K,V> Matcher<Map<? extends K,? extends V>> hasEntry(K key, V value) {
        return new IsMapContaining<K,V>(equalTo(key), equalTo(value));
    }
    
    @Factory
    public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> keyMatcher) {
        return new IsMapContaining<K,Object>(keyMatcher, anything());
    }

    @Factory
    public static <K> Matcher<Map<? extends K, ?>> hasKey(K key) {
        return new IsMapContaining<K,Object>(equalTo(key), anything());
    }

    @Factory
    public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> valueMatcher) {
        return new IsMapContaining<Object,V>(anything(), valueMatcher);
    }

    @Factory
    public static <V> Matcher<Map<?, ? extends V>> hasValue(V value) {
        return new IsMapContaining<Object,V>(anything(), equalTo(value));
    }
}
