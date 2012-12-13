package org.hamcrest.collection;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.Map;
import java.util.TreeMap;

public class IsMapContainingTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return IsMapContaining.hasEntry("irrelevant", "irrelevant");
    }

    public void testMatchesMapContainingMatchingKeyAndValue() {
        Map<String,Integer> map = new TreeMap<String,Integer>();
        map.put("a", 1);
        map.put("b", 2);

        assertMatches("matcherA", hasEntry(equalTo("a"), equalTo(1)), map);
        assertMatches("matcherB", hasEntry(equalTo("b"), equalTo(2)), map);
        assertMismatchDescription("map was [<a=1>, <b=2>]", hasEntry(equalTo("c"), equalTo(3)), map);
    }

//    no longer compiles. SF
//    public void testMatchesMapContainingMatchingKeyAndValueWithoutGenerics() {
//        Map map = new HashMap();
//        map.put("a", 1);
//        map.put("b", 2);
//
//        assertMatches("matcherA", hasEntry(equalTo("a"), equalTo(1)), map);
//        assertMatches("matcherB", hasEntry(equalTo("b"), equalTo(2)), map);
//        assertDoesNotMatch("matcherC", hasEntry(equalTo("c"), equalTo(3)), map);
//    }
//
    public void testDoesNotMatchNull() {
        assertMismatchDescription("was null", hasEntry(anything(), anything()), null);
    }

    public void testHasReadableDescription() {
        assertDescription("map containing [\"a\"-><2>]", hasEntry(equalTo("a"), (equalTo(2))));
    }

    // Remaining code no longer compiles, thanks to generics. I think that's a good thing, but
    // I still need to investigate how this behaves with code that doesn't use generics.
    // I expect ClassCastExceptions will be thrown.
    // -Joe.
    
//    public void testDoesNotMatchAnObjectThatIsNotAMap() {
//        assertDoesNotMatch("should not matches a string",
//                mapContaining(ANYTHING, ANYTHING), "not a map");
//    }
}
