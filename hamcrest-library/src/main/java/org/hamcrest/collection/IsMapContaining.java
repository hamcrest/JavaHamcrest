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

    @Override
    public void describeTo(Description description) {
        description.appendText("map containing [")
                   .appendDescriptionOf(keyMatcher)
                   .appendText("->")
                   .appendDescriptionOf(valueMatcher)
                   .appendText("]");
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one entry whose key satisfies the specified <code>keyMatcher</code> <b>and</b> whose
     * value satisfies the specified <code>valueMatcher</code>.
     * <p/>
     * For example:
     * <pre>assertThat(myMap, hasEntry(equalTo("bar"), equalTo("foo")))</pre>
     * 
     * @param keyMatcher
     *     the key matcher that, in combination with the valueMatcher, must be satisfied by at least one entry
     * @param valueMatcher
     *     the value matcher that, in combination with the keyMatcher, must be satisfied by at least one entry
     */
    @Factory
    public static <K,V> Matcher<Map<? extends K,? extends V>> hasEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return new IsMapContaining<K,V>(keyMatcher, valueMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one entry whose key equals the specified <code>key</code> <b>and</b> whose value equals the
     * specified <code>value</code>.
     * <p/>
     * For example:
     * <pre>assertThat(myMap, hasEntry("bar", "foo"))</pre>
     *  
     * @param key
     *     the key that, in combination with the value, must be describe at least one entry
     * @param value
     *     the value that, in combination with the key, must be describe at least one entry
     */
    @Factory
    public static <K,V> Matcher<Map<? extends K,? extends V>> hasEntry(K key, V value) {
        return new IsMapContaining<K,V>(equalTo(key), equalTo(value));
    }
    
    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one key that satisfies the specified matcher.
     * <p/>
     * For example:
     * <pre>assertThat(myMap, hasKey(equalTo("bar")))</pre>
     * 
     * @param keyMatcher
     *     the matcher that must be satisfied by at least one key
     */
    @Factory
    public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> keyMatcher) {
        return new IsMapContaining<K,Object>(keyMatcher, anything());
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one key that is equal to the specified key.
     * <p/>
     * For example:
     * <pre>assertThat(myMap, hasKey("bar"))</pre>
     * 
     * @param key
     *     the key that satisfying maps must contain
     */
    @Factory
    public static <K> Matcher<Map<? extends K, ?>> hasKey(K key) {
        return new IsMapContaining<K,Object>(equalTo(key), anything());
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one value that satisfies the specified valueMatcher.
     * <p/>
     * For example:
     * <pre>assertThat(myMap, hasValue(equalTo("foo")))</pre>
     * 
     * @param valueMatcher
     *     the matcher that must be satisfied by at least one value
     */
    @Factory
    public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> valueMatcher) {
        return new IsMapContaining<Object,V>(anything(), valueMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one value that is equal to the specified value.
     * <p/>
     * For example:
     * <pre>assertThat(myMap, hasValue("foo"))</pre>
     * 
     * @param value
     *     the value that satisfying maps must contain
     */
    @Factory
    public static <V> Matcher<Map<?, ? extends V>> hasValue(V value) {
        return new IsMapContaining<Object,V>(anything(), equalTo(value));
    }
}
