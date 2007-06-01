package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsAnything;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Map;
import java.util.Map.Entry;

public class IsMapContaining<K,V> extends TypeSafeMatcher<Map<K, V>> {

    private final Matcher<K> keyMatcher;
    private final Matcher<V> valueMatcher;

    public IsMapContaining(Matcher<K> keyMatcher, Matcher<V> valueMatcher) {
        this.keyMatcher = keyMatcher;
        this.valueMatcher = valueMatcher;
    }

    public boolean matchesSafely(Map<K, V> map) {
        for (Entry<K, V> entry : map.entrySet()) {
            if (keyMatcher.matches(entry.getKey()) && valueMatcher.matches(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    public void describeTo(Description description) {
        description.appendText("map containing [")
                   .appendDescriptionOf(keyMatcher)
                   .appendText("->")
			       .appendDescriptionOf(valueMatcher)
			       .appendText("]");
    }

    @Factory
    public static <K,V> Matcher<Map<K,V>> hasEntry(Matcher<K> keyMatcher, Matcher<V> valueMatcher) {
        return new IsMapContaining<K,V>(keyMatcher, valueMatcher);
    }

    @Factory
    public static <K,V> Matcher<Map<K,V>> hasEntry(K key, V value) {
        return hasEntry(equalTo(key), equalTo(value));
    }

    @Factory
    public static <K,V> Matcher<Map<K,V>> hasKey(Matcher<K> keyMatcher) {
        return hasEntry(keyMatcher, IsAnything.<V>anything());
    }

    @Factory
    public static <K,V> Matcher<Map<K,V>> hasKey(K key) {
        return hasKey(equalTo(key));
    }

    @Factory
    public static <K,V> Matcher<Map<K,V>> hasValue(Matcher<V> valueMatcher) {
        return hasEntry(IsAnything.<K>anything(), valueMatcher);
    }

    @Factory
    public static <K,V> Matcher<Map<K,V>> hasValue(V value) {
        return hasValue(equalTo(value));
    }
}
