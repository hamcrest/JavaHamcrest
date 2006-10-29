package org.hamcrest.collection;

import static org.hamcrest.collection.IsArray.array;
import static org.hamcrest.core.IsEqual.eq;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

@SuppressWarnings("unchecked")
public class IsArrayTest extends AbstractMatcherTest {

	protected Matcher<?> createMatcher() {
        return array(eq("irrelevant"));
    }

    public void testMatchesAnArrayThatMatchingesAllTheElementMatchers() {
        assertMatches("should matches array that contains 'a'",
                array(eq("a"), eq("b"), eq("c")), new String[]{"a", "b", "c"});
    }
    
    public void testDoesNotMatchAnArrayWhenElementsDoNotMatch() {
        assertDoesNotMatch("should not matches array with different elements",
                array(eq("a"), eq("b")), new String[]{"b", "c"});
    }
    
    public void testDoesNotMatchAnArrayOfDifferentSize() {
        assertDoesNotMatch("should not matches larger array",
                           array(eq("a"), eq("b")), new String[]{"a", "b", "c"});
        assertDoesNotMatch("should not matches smaller array",
                           array(eq("a"), eq("b")), new String[]{"a"});
    }
    
    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null",
                array(eq("a")), null);
    }
    
    public void testHasAReadableDescription() {
        assertDescription("[eq(\"a\"), eq(\"b\")]", array(eq("a"), eq("b")));
    }
}
