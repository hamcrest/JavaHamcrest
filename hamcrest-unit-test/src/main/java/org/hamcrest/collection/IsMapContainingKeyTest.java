package org.hamcrest.collection;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContainingKey.hasKey;

public class IsMapContainingKeyTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasKey("foo");
    }

    public void testMatchesSingletonMapContainingKey() {
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        
        assertMatches("Matches single key", hasKey("a"), map);
    }
    
    public void testMatchesMapContainingKey() {
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        assertMatches("Matches a", hasKey("a"), map);
        assertMatches("Matches c", hasKey("c"), map);
    }
    

//    No longer compiles
//    public void testMatchesMapContainingKeyWithNoGenerics() {
//        Map map = new HashMap();
//        map.put("a", 1);
//        map.put("b", 2);
//        map.put("c", 3);
//
//        assertMatches("Matches a", hasKey("a"), map);
//        assertMatches("Matches c", hasKey("c"), map);
//    }

    public void testMatchesMapContainingKeyWithIntegerKeys() throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "A");
        map.put(2, "B");

        assertThat(map, hasKey(Integer.valueOf(1)));
    }

    public void testMatchesMapContainingKeyWithNumberKeys() throws Exception {
        Map<Number, String> map = new HashMap<Number, String>();
        map.put(1, "A");
        map.put(2, "B");

        assertThat(map, hasKey((Number) Integer.valueOf(1)));

        // very ugly version!
        assertThat(map, IsMapContainingKey.<Number>hasKey(Integer.valueOf(1)));

        assertThat(map, hasKey(Integer.valueOf(1)));
    }

    public void testHasReadableDescription() {
        assertDescription("map with key \"a\"", hasKey("a"));
    }
    
    public void testDoesNotMatchEmptyMap() {
        assertDoesNotMatch("Empty map", hasKey("Foo"), new HashMap<String,Integer>());
    }
    
    public void testDoesNotMatchMapMissingKey() {
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        assertDoesNotMatch("Map without matching key", hasKey("d"), map);
    }
}
