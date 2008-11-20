package org.hamcrest.collection;

import static org.hamcrest.collection.IsArray.array;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

@SuppressWarnings("unchecked")
public class IsArrayTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return array(equalTo("irrelevant"));
    }

    public void testMatchesAnArrayThatMatchesAllTheElementMatchers() {
        assertMatches("should match array with matching elements",
                array(equalTo("a"), equalTo("b"), equalTo("c")), new String[]{"a", "b", "c"});
    }
    
    public void testDoesNotMatchAnArrayWhenElementsDoNotMatch() {
        assertDoesNotMatch("should not match array with different elements",
                array(equalTo("a"), equalTo("b")), new String[]{"b", "c"});
    }
    
    public void testDoesNotMatchAnArrayOfDifferentSize() {
        assertDoesNotMatch("should not match larger array",
                           array(equalTo("a"), equalTo("b")), new String[]{"a", "b", "c"});
        assertDoesNotMatch("should not match smaller array",
                           array(equalTo("a"), equalTo("b")), new String[]{"a"});
    }
    
    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not match null",
                array(equalTo("a")), null);
    }
    
    public void testHasAReadableDescription() {
        assertDescription("[\"a\", \"b\"]", array(equalTo("a"), equalTo("b")));
    }
}
