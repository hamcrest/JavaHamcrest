package org.hamcrest.collection;

import static org.hamcrest.collection.IsArray.array;
import static org.hamcrest.core.IsEqual.eq;

import org.hamcrest.AbstractMatcherTest;

public class IsArrayTest extends AbstractMatcherTest {
    public void testMatchesAnArrayThatMatchingesAllTheElementMatchers() {
        assertMatches("should match array that contains 'a'",
                array(eq("a"), eq("b"), eq("c")), new String[]{"a", "b", "c"});
    }
    
    public void testDoesNotMatchAnArrayWhenElementsDoNotMatch() {
        assertDoesNotMatch("should not match array with different elements",
                array(eq("a"), eq("b")), new String[]{"b", "c"});
    }
    
    public void testDoesNotMatchAnArrayOfDifferentSize() {
        assertDoesNotMatch("should not match larger array",
                           array(eq("a"), eq("b")), new String[]{"a", "b", "c"});
        assertDoesNotMatch("should not match smaller array",
                           array(eq("a"), eq("b")), new String[]{"a"});
    }
    
    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not match null",
                array(eq("a")), null);
    }
    
    public void testHasAReadableDescription() {
        assertDescription("[eq(\"a\"), eq(\"b\")]", array(eq("a"), eq("b")));
    }
}
