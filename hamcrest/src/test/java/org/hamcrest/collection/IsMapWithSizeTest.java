package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.core.IsEqual.equalTo;

public final class IsMapWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return aMapWithSize(7);
    }

    public void testMatchesWhenSizeIsCorrect() {
        assertMatches("correct size", aMapWithSize(equalTo(2)), mapWithKeys("a", "b"));
        assertDoesNotMatch("incorrect size", aMapWithSize(equalTo(2)), mapWithKeys("a", "b", "c"));
    }

    public void testMatchesMapWhenSizeIsCorrectUsingObjectElementType() {
        Map<Object, Object> map = mapWithKeys(new Object(), new Object());
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertDoesNotMatch("incorrect size", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesMapWhenSizeIsCorrectUsingStringElementType() {
        Map<String, Integer> map = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertDoesNotMatch("incorrect size", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesMapWhenSizeIsCorrectUsingWildcardElementType() {
        Map<?, ?> map = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertDoesNotMatch("incorrect size", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesListWhenSizeIsCorrectUsingObjectElementType() {
        Map<Object, Object> map = mapWithKeys(new Object(), new Object());
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertDoesNotMatch("incorrect size", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesListWhenSizeIsCorrectUsingStringElementType() {
        Map<String, Integer> map = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertDoesNotMatch("incorrect size", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesListWhenSizeIsCorrectUsingWildcardElementType() {
        Map<?, ?> map = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertDoesNotMatch("incorrect size", aMapWithSize(equalTo(3)), map);
    }

    public void testProvidesConvenientShortcutForHasSizeEqualTo() {
        assertMatches("correct size", aMapWithSize(2), mapWithKeys(new Object(), new Object()));
        assertDoesNotMatch("incorrect size", aMapWithSize(2), mapWithKeys(new Object(), new Object(), new Object()));
    }

    public void testHasAReadableDescription() {
        assertDescription("a map with size <3>", aMapWithSize(equalTo(3)));
    }
    
    public void testCompilesWithATypedMap() {
      Map<String, Integer> arrayList = new HashMap<String, Integer>();
      MatcherAssert.assertThat(arrayList, aMapWithSize(0));
    }

    public void testOnMismatchProvidesInsightfulDebuggingInformation() {
        assertMismatchDescription("map size was <2>. Actual values: [<k1=a>, <k2=b>]",
                aMapWithSize(equalTo(1)), mapWithKeysAndValues(asList("k1", "k2"), asList("a", "b")));
    }

    private static <K, V> Map<K, V> mapWithKeys(K... keys) {
        final Map<K, V> result = new HashMap<K, V>();
        for (K key : keys) {
            result.put(key, null);
        }
        return result;
    }

    private static <K, V> Map<K, V> mapWithKeysAndValues(List<K> keys, List<V> values) {
        final Map<K, V> result = new HashMap<K, V>();
        Iterator<V> valuesIt = values.iterator();
        for (K key : keys) {
            result.put(key, valuesIt.next());
        }
        return result;
    }
}
