package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.collection.IsMapContaining.mapContaining;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.HashMap;
import java.util.Map;

public class IsMapContainingTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return mapContaining("irrelevant", "irrelevant");
    }

    public void testMatchesMapContainingMatchingKeyAndValue() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("a", 1);
        map.put("b", 2);

        assertMatches("matcherA", mapContaining(equalTo("a"), equalTo(1)), map);
        assertMatches("matcherB", mapContaining(equalTo("b"), equalTo(2)), map);
        assertDoesNotMatch("matcherC", mapContaining(equalTo("c"), equalTo(3)), map);
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null",
                mapContaining(anything(), anything()), null);
    }

    public void testHasReadableDescription() {
        assertDescription("map containing [\"a\"-><2>]",
                mapContaining(equalTo("a"), (equalTo(2))));
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
