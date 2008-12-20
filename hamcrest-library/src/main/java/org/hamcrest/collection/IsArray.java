package org.hamcrest.collection;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher for array whose elements satisfy a sequence of matchers.
 * The array size must equal the number of element matchers.
 */
public class IsArray<T> extends TypeSafeMatcher<T[]> {
    private final Matcher<? super T>[] elementMatchers;
    
    public IsArray(Matcher<? super T>[] elementMatchers) {
        this.elementMatchers = elementMatchers.clone();
    }
    
    @Override
    public boolean matchesSafely(T[] array) {
        if (array.length != elementMatchers.length) return false;
        
        for (int i = 0; i < array.length; i++) {
            if (!elementMatchers[i].matches(array[i])) return false;
        }
        
        return true;
    }

    @Override
    public void describeMismatchSafely(T[] actual, Description mismatchDescription) {
      if (actual.length != elementMatchers.length) {
        mismatchDescription.appendText("array length was " + actual.length);
        return;
      }
      for (int i = 0; i < actual.length; i++) {
        if (!elementMatchers[i].matches(actual[i])) {
          mismatchDescription.appendText("element " + i + " was ").appendValue(actual[i]);
          return;
        }
      }
    }

    public void describeTo(Description description) {
        description.appendList(descriptionStart(), descriptionSeparator(), descriptionEnd(), 
                               Arrays.asList(elementMatchers));
    }
    
    /**
     * Returns the string that starts the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionStart() {
        return "[";
    }

    /**
     * Returns the string that separates the elements in the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionSeparator() {
        return ", ";
    }

    /**
     * Returns the string that ends the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionEnd() {
        return "]";
    }
    
    /**
     * Evaluates to true only if each matcher[i] is satisfied by array[i].
     */
    public static <T> IsArray<T> array(Matcher<? super T>... elementMatchers) {
        return new IsArray<T>(elementMatchers);
    }

}
