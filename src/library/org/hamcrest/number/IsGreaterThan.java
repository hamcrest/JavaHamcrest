/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Is the value less than or greater than another {@link java.lang.Comparable} value?
 */
public class IsGreaterThan<T extends Comparable<T>> extends TypeSafeMatcher<T> {
    private final Comparable<T> compareTo;
    
    public IsGreaterThan(Comparable<T> compareTo) {
        this.compareTo = compareTo;
    }
    
    public boolean matchesSafely(T item) {
        return compareTo.compareTo(item) < 0;
    }
    
    public void describeTo(Description description) {
        description.appendText("a value greater than ");
        description.appendValue(compareTo);
    }
}
