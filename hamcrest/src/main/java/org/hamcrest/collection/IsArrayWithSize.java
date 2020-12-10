package org.hamcrest.collection;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if array size satisfies a nested matcher.
 */
public class IsArrayWithSize extends FeatureMatcher<Object[], Integer> {
    public IsArrayWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an array with size","array size");
    }

    @Override
    protected Integer featureValueOf(Object[] actual) {
      return actual.length;
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * satisfies the specified matcher.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(equalTo(2)))</pre>
     * 
     * @param sizeMatcher
     *     a matcher for the length of an examined array
     */
    public static Matcher<Object[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsArrayWithSize(sizeMatcher);
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * equals the specified <code>size</code>.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(2))</pre>
     * 
     * @param size
     *     the length that an examined array must have for a positive match
     */
    public static Matcher<Object[]> arrayWithSize(int size) {
        return arrayWithSize(equalTo(size));
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * is zero.
     * For example:
     * <pre>assertThat(new String[0], emptyArray())</pre>
     * 
     */
    public static Matcher<Object[]> emptyArray() {
        return describedAs("an empty array", arrayWithSize(0));
    }
}
