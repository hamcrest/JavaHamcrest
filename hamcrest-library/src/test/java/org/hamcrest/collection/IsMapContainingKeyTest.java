package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;

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

        assertThat(map, hasKey((Number)1));

        // TODO: work out the correct sprinkling of wildcards to get this to work!
//        assertThat(map, hasKey(1));
    }

    public void testHasReadableDescription() {
        assertDescription("map containing [\"a\"->ANYTHING]", hasKey("a"));
    }
    
    public void testDoesNotMatchEmptyMap() {
        assertMismatchDescription("map was []", hasKey("Foo"), new HashMap<String,Integer>());
    }
    
    public void testDoesNotMatchMapMissingKey() {
        Map<String,Integer> map = new TreeMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        assertMismatchDescription("map was [<a=1>, <b=2>, <c=3>]", hasKey("d"), map);
    }
}
