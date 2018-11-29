package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.core.IsEqual.equalTo;

public final class IsMapWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return aMapWithSize(7);
    }

    public void testMatchesWhenSizeIsCorrect() {
        assertMatches("correct size", aMapWithSize(equalTo(2)), mapWithKeys("a", "b"));
        assertMismatchDescription("map size was <3>", aMapWithSize(equalTo(2)), mapWithKeys("a", "b", "c"));
    }

    public void testMatchesMapWhenSizeIsCorrectUsingObjectElementType() {
        Map<Object, Object> map = mapWithKeys(new Object(), new Object());
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesMapWhenSizeIsCorrectUsingStringElementType() {
        Map<String, Integer> map = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesMapWhenSizeIsCorrectUsingWildcardElementType() {
        Map<?, ?> map = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesListWhenSizeIsCorrectUsingObjectElementType() {
        Map<Object, Object> map = mapWithKeys(new Object(), new Object());
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesListWhenSizeIsCorrectUsingStringElementType() {
        Map<String, Integer> list = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), list);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), list);
    }

    public void testMatchesListWhenSizeIsCorrectUsingWildcardElementType() {
        Map<?, ?> list = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), list);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), list);
    }

    public void testProvidesConvenientShortcutForHasSizeEqualTo() {
        assertMatches("correct size", aMapWithSize(2), mapWithKeys(new Object(), new Object()));
        assertMismatchDescription("map size was <3>", aMapWithSize(2), mapWithKeys(new Object(), new Object(), new Object()));
    }

    public void testHasAReadableDescription() {
        assertDescription("a map with size <3>", aMapWithSize(equalTo(3)));
    }
    
    public void testCompilesWithATypedMap() {
      Map<String, Integer> arrayList = new HashMap<String, Integer>();
      MatcherAssert.assertThat(arrayList, aMapWithSize(0));
    }
    
    private static <K, V> Map<K, V> mapWithKeys(K... keys) {
        final Map<K, V> result = new HashMap<K, V>();
        for (K key : keys) {
            result.put(key, null);
        }
        return result;
    }
}
