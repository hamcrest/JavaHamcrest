package org.hamcrest.collection;


import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.collection.IsMapContaining.hasValue;

public class IsMapContainingValueTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasValue("foo");
    }

    public void testHasReadableDescription() {
        assertDescription("map containing [ANYTHING->\"a\"]", hasValue("a"));
    }
    
    public void testDoesNotMatchEmptyMap() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        assertMismatchDescription("map was []", hasValue(1), map);
    }
    
    public void testMatchesSingletonMapContainingValue() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("a", 1);
        
        assertMatches("Singleton map", hasValue(1), map);
    }

    public void testMatchesMapContainingValue() {
        Map<String,Integer> map = new TreeMap<String,Integer>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        assertMatches("hasValue 1", hasValue(1), map);      
        assertMatches("hasValue 3", hasValue(3), map);      
        assertMismatchDescription("map was [<a=1>, <b=2>, <c=3>]", hasValue(4), map);      
    }
}
