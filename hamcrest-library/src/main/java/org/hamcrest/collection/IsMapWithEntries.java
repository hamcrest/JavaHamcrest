package org.hamcrest.collection;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.Map;
import java.util.Set;

public class IsMapWithEntries<K, V> extends FeatureMatcher<Map<? extends K, ? extends V>, Set<? extends Map.Entry<? extends K, ? extends V>>> {

    public IsMapWithEntries(Matcher<? super Set<? extends Map.Entry<? extends K, ? extends V>>> entriesMatcher) {
        super(entriesMatcher, "a map with entries", "map entries");
    }

    @Override
    protected Set<? extends Map.Entry<? extends K, ? extends V>> featureValueOf(Map<? extends K, ? extends V> actual) {
        return actual.entrySet();
    }

    /**
     * Creates a matcher for {@link Map}s matching when the examined {@link Map}'s set of entries
     * satisfies the specified <code>entriesMatcher</code>.
     * For example:
     * <pre>assertThat(myMap, hasEntries(hasSize(2)))</pre>
     *
     * @param entriesMatcher
     *     the matcher that must be satisfied by the set of entries
     */
    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntries(Matcher<? super Set<? extends Map.Entry<? extends K, ? extends V>>> entriesMatcher) {
        return new IsMapWithEntries<>(entriesMatcher);
    }

    /**
     * Creates a matcher for {@link Map}s matching when the examined {@link Map}'s set of entries
     * contains, in any order, entries satisfying the specified <code>entriesMatchers</code>.
     * For example:
     * <pre>assertThat(myMap, hasEntries(entry("a key"), entry("another key")))</pre>
     *
     * @param entriesMatchers
     *     the matchers that must be satisfied by the entries
     */
    @SafeVarargs
    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntries(Matcher<? super Map.Entry<? extends K, ? extends V>>... entriesMatchers) {
        return new IsMapWithEntries<>(IsIterableContainingInAnyOrder.containsInAnyOrder(entriesMatchers));
    }

}
