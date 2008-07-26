package org.hamcrest.collection;

import java.util.Arrays;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.collection.IsIterableContainingSingleItem.hasSingleItem;

import static java.util.Arrays.asList;

public class IsIterableContainingSingleItemTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasSingleItem(4);
    }
    
    public void testMatchesSingleItemIterable() throws Exception {
        assertMatches("singleton with matching item", hasSingleItem(4), asList(4));
    }
    
    public void testDoesNotMatcheSingletonWithMismatchingItem() throws Exception {
        assertDoesNotMatch("singleton with incorrect item", hasSingleItem(6), asList(4));
    }
    
    public void testDoesNotMatchEmptyList() throws Exception {
        assertDoesNotMatch("empty list", hasSingleItem(4), Arrays.<Integer>asList());
    }
    
    public void testDoesNotMatchMultipleItemList() throws Exception {
        assertDoesNotMatch("multiple item list", hasSingleItem(4), asList(4, 3));
    }

    public void testHasAReadableDescription() {
        assertDescription("a singleton iterable with [<4>]", hasSingleItem(4));
    }
}
