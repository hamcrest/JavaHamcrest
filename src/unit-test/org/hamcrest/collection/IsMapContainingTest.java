package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.collection.IsMapContaining.mapContaining;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.eq;

import java.util.HashMap;
import java.util.Map;

public class IsMapContainingTest extends AbstractMatcherTest {

    public void testMatchesMapContainingMatchingKeyAndValue() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("a", 1);
        map.put("b", 2);

        assertMatches("matcherA", mapContaining(eq("a"), eq(1)), map);
        assertMatches("matcherB", mapContaining(eq("b"), eq(2)), map);
        assertDoesNotMatch("matcherC", mapContaining(eq("c"), eq(3)), map);
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not match null",
                mapContaining(anything(), anything()), null);
    }

    public void testHasReadableDescription() {
        assertDescription("map containing [eq(\"a\")->eq(<2>)]",
                mapContaining(eq("a"), (eq(2))));
    }

    // Remaining code no longer compiles, thanks to generics. I think that's a good thing, but
    // I still need to investigate how this behaves with code that doesn't use generics.
    // I expect ClassCastExceptions will be thrown.
    // -Joe.
    
//    public void testDoesNotMatchAnObjectThatIsNotAMap() {
//        assertDoesNotMatch("should not match a string",
//                mapContaining(ANYTHING, ANYTHING), "not a map");
//    }
}
