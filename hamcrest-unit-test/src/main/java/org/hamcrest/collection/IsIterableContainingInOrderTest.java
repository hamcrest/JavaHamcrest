package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsIterableContainingInOrderTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return contains(1, 2);
    }
    
    public void testMatchingSingleItemIterable() throws Exception {
        assertMatches("Single item iterable", contains(1), asList(1));
    }
    
    public void testMatchingMultipleItemIterable() throws Exception {
        assertMatches("Multiple item iterable", contains(1, 2, 3), asList(1, 2, 3));
    }
    
    public void testDoesNotMatchWithMoreElementsThanExpected() throws Exception {
        assertMismatchDescription("iterable was [<1>, <2>, <3>, <4>]", contains(1, 2, 3), asList(1, 2, 3, 4));
    }
    
    public void testDoesNotMatchWithLessElementsThanExpected() throws Exception {
        assertMismatchDescription("iterable was [<1>, <2>]", contains(1, 2, 3), asList(1, 2));
    }
    
    public void testDoesNotMatchIfSingleItemMismatches() throws Exception {
        assertMismatchDescription("iterable was [<2>]", contains(3), asList(2));  
    }
    
    public void testDoesNotMatchIfOneOfMultipleItemsMismatch() throws Exception {
        assertMismatchDescription("iterable was [<1>, <2>, <4>]", contains(1, 2, 3), asList(1, 2, 4));
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable over [<1>, <2>]", contains(1, 2));
    }
}
