package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Map;

import static java.util.Map.Entry;

public class IsMapEntry<K, V> extends TypeSafeDiagnosingMatcher<Map.Entry<? extends K, ? extends V>> {

    private final Matcher<? super K> keyMatcher;
    private final Matcher<? super V> valueMatcher;

    public IsMapEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        this.keyMatcher = keyMatcher;
        this.valueMatcher = valueMatcher;
    }

    @Override
    protected boolean matchesSafely(Map.Entry<? extends K, ? extends V> item, Description mismatchDescription) {
        boolean matches = true;

        if (!keyMatcher.matches(item.getKey())) {
            matches = false;
            mismatchDescription.appendText("key ");
            keyMatcher.describeMismatch(item.getKey(), mismatchDescription);
        }

        if (valueMatcher != null && !valueMatcher.matches(item.getValue())) {
            if (!matches) mismatchDescription.appendText(" and ");
            matches = false;
            mismatchDescription.appendText("value ");
            valueMatcher.describeMismatch(item.getValue(), mismatchDescription);
        }

        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an entry with key ").appendDescriptionOf(keyMatcher);
        if (valueMatcher != null) description.appendText(" and value ").appendDescriptionOf(valueMatcher);
    }

    /**
     * Creates a matcher for {@link Entry}s matching when the examined {@link Entry} has a key which satisfies
     * the specified <code>keyMatcher</code>, and a value which satisfies the specified <code>valueMatcher</code>.
     * For example:
     * <pre>assertThat(myMap.keySet(), hasItem(entry(equalTo("key"), notNullValue())))</pre>
     *
     * @param keyMatcher   the matcher that must be satisfied by the key
     * @param valueMatcher the matcher that must be satisfied by the value
     */
    public static <K, V> Matcher<? super Map.Entry<? extends K, ? extends V>> entry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return new IsMapEntry<>(keyMatcher, valueMatcher);
    }

    /**
     * Creates a matcher for {@link Entry}s matching when the examined {@link Entry} has a key which satisfies
     * the specified <code>keyMatcher</code>; the value is ignored.
     * For example:
     * <pre>assertThat(myMap.keySet(), hasItem(entry(equalTo("key"))))</pre>
     *
     * @param keyMatcher the matcher that must be satisfied by the key
     */
    public static <K> Matcher<? super Map.Entry<? extends K, ?>> entry(Matcher<? super K> keyMatcher) {
        return new IsMapEntry<>(keyMatcher, null);
    }

    /**
     * Creates a matcher for {@link Entry}s matching when the examined {@link Entry} has the specified
     * <code>key</code>, and a value which satisfies the specified <code>valueMatcher</code>.
     * For example:
     * <pre>assertThat(myMap.keySet(), hasItem(entry("key", notNullValue())))</pre>
     *
     * @param key          the required key
     * @param valueMatcher the matcher that must be satisfied by the value
     */
    public static <K, V> Matcher<? super Map.Entry<? extends K, ? extends V>> entry(K key, Matcher<? super V> valueMatcher) {
        return new IsMapEntry<>(Matchers.equalTo(key), valueMatcher);
    }

    /**
     * Creates a matcher for {@link Entry}s matching when the examined {@link Entry} has the specified
     * <code>key</code>; the value is ignored.
     * For example:
     * <pre>assertThat(myMap.keySet(), hasItem(entry("key")))</pre>
     *
     * @param key the required key
     */
    public static <K> Matcher<? super Map.Entry<? extends K, ?>> entry(K key) {
        return new IsMapEntry<>(Matchers.equalTo(key), null);
    }

}
