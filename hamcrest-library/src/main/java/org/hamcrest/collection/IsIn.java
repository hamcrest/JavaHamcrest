package org.hamcrest.collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;

public class IsIn<T> extends BaseMatcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection) {
        this.collection = collection;
    }
    
    public IsIn(T[] elements) {
        collection = Arrays.asList(elements);
    }
    
    @Override
    public boolean matches(Object o) {
        return collection.contains(o);
    }

    @Override
    public void describeTo(Description buffer) {
        buffer.appendText("one of ");
        buffer.appendValueList("{", ", ", "}", collection);
    }
    
    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified collection.
     * 
     * @param collection
     *     the collection in which matching items must be found
     * 
     */
    @Factory
    public static <T> Matcher<T> isIn(Collection<T> collection) {
        return new IsIn<T>(collection);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified array.
     * 
     * @param elements
     *     the array in which matching items must be found
     * 
     */
    @Factory
    public static <T> Matcher<T> isIn(T[] elements) {
        return new IsIn<T>(elements);
    }
    
    /**
     * Creates a matcher that matches when the examined object is equal to one of the
     * specified elements.
     * 
     * @param elements
     *     the elements amongst which matching items will be found 
     * 
     */
    @Factory
    public static <T> Matcher<T> isOneOf(T... elements) {
        return isIn(elements);
    }
}
