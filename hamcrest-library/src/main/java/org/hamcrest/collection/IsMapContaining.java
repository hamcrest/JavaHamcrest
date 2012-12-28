package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;
import java.util.Map.Entry;

import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsMapContaining<M extends Map<?, ?>> extends TypeSafeMatcher<M> {
    private final Matcher<?> keyMatcher;
    private final Matcher<?> valueMatcher;

    public IsMapContaining(Matcher<?> keyMatcher, Matcher<?> valueMatcher) {
        this.keyMatcher = keyMatcher;
        this.valueMatcher = valueMatcher;
    }

    @Override
    public boolean matchesSafely(M map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            if (keyMatcher.matches(entry.getKey()) && valueMatcher.matches(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeMismatchSafely(M map, Description mismatchDescription) {
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
    public static <K, V, M extends Map<?,?>> Matcher<M> hasEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return new IsMapContaining<M>(keyMatcher, valueMatcher);
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
    public static <K, V, M extends Map<?,?>> Matcher<M> hasEntry(K key, V value) {
        return hasEntry(equalTo(key), equalTo(value));
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
    public static <K, M extends Map<?,?>> Matcher<M> hasKey(Matcher<? super K> keyMatcher) {
        return hasEntry(keyMatcher, anything());
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
    public static <K, M extends Map<?,?>> Matcher<M> hasKey(K key) {
        return hasKey(equalTo(key));
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
    public static <V, M extends Map<?,?>> Matcher<M> hasValue(Matcher<? super V> valueMatcher) {
        return hasEntry(anything(), valueMatcher);
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
    public static <V, M extends Map<?,?>> Matcher<M> hasValue(V value) {
        return hasValue(equalTo(value));
    }
}
