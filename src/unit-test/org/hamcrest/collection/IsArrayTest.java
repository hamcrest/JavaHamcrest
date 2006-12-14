package org.hamcrest.collection;

import static org.hamcrest.collection.IsArray.array;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

@SuppressWarnings("unchecked")
public class IsArrayTest extends AbstractMatcherTest {

	protected Matcher<?> createMatcher() {
        return array(equalTo("irrelevant"));
    }

    public void testMatchesAnArrayThatMatchingesAllTheElementMatchers() {
        assertMatches("should matches array that contains 'a'",
                array(equalTo("a"), equalTo("b"), equalTo("c")), new String[]{"a", "b", "c"});
    }
    
    public void testDoesNotMatchAnArrayWhenElementsDoNotMatch() {
        assertDoesNotMatch("should not matches array with different elements",
                array(equalTo("a"), equalTo("b")), new String[]{"b", "c"});
    }
    
    public void testDoesNotMatchAnArrayOfDifferentSize() {
        assertDoesNotMatch("should not matches larger array",
                           array(equalTo("a"), equalTo("b")), new String[]{"a", "b", "c"});
        assertDoesNotMatch("should not matches smaller array",
                           array(equalTo("a"), equalTo("b")), new String[]{"a"});
    }
    
    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null",
                array(equalTo("a")), null);
    }
    
    public void testHasAReadableDescription() {
        assertDescription("[\"a\", \"b\"]", array(equalTo("a"), equalTo("b")));
    }
}
