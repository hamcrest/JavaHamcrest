package org.hamcrest.collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
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
    
    @SuppressWarnings("SuspiciousMethodCalls")
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
     * For example:
     * <pre>assertThat("foo", isIn(Arrays.asList("bar", "foo")))</pre>
     * 
     * @deprecated use is(in(...)) instead
     * 
     * @param collection
     *     the collection in which matching items must be found
     * 
     */
    @Deprecated
    public static <T> Matcher<T> isIn(Collection<T> collection) {
        return in(collection);
    }
    
    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified collection.
     * For example:
     * <pre>assertThat("foo", is(in(Arrays.asList("bar", "foo"))))</pre>
     * 
     * @param collection
     *     the collection in which matching items must be found
     * 
     */
    public static <T> Matcher<T> in(Collection<T> collection) {
        return new IsIn<T>(collection);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified array.
     * For example:
     * <pre>assertThat("foo", isIn(new String[]{"bar", "foo"}))</pre>
     * 
     * @deprecated use is(in(...)) instead
     * 
     * @param elements
     *     the array in which matching items must be found
     * 
     */
    @Deprecated
    public static <T> Matcher<T> isIn(T[] elements) {
        return in(elements);
    }
    
    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified array.
     * For example:
     * <pre>assertThat("foo", is(in(new String[]{"bar", "foo"})))</pre>
     * 
     * @param elements
     *     the array in which matching items must be found
     * 
     */
    public static <T> Matcher<T> in(T[] elements) {
        return new IsIn<T>(elements);
    }
    
    /**
     * Creates a matcher that matches when the examined object is equal to one of the
     * specified elements.
     * For example:
     * <pre>assertThat("foo", isOneOf("bar", "foo"))</pre>
     * 
     * @deprecated use is(oneOf(...)) instead
     * 
     * @param elements
     *     the elements amongst which matching items will be found 
     * 
     */
    @Deprecated
    public static <T> Matcher<T> isOneOf(T... elements) {
        return oneOf(elements);
    }
    
    /**
     * Creates a matcher that matches when the examined object is equal to one of the
     * specified elements.
     * For example:
     * <pre>assertThat("foo", is(oneOf("bar", "foo")))</pre>
     *  
     * @param elements
     *     the elements amongst which matching items will be found 
     * 
     */
    public static <T> Matcher<T> oneOf(T... elements) {
        return in(elements);
    }
}
