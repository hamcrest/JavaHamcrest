package org.hamcrest.collection;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if array size satisfies a nested matcher.
 */
public class IsArrayWithSize<E> extends FeatureMatcher<E[], Integer> {
    public IsArrayWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an array with size","array size");
    }

    @Override
    protected Integer featureValueOf(E[] actual) {
      return actual.length;
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * satisfies the specified matcher.
     * <p/>
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(equalTo(2)))</pre>
     * 
     * @param sizeMatcher
     *     a matcher for the length of an examined array
     */
    @Factory
    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsArrayWithSize<E>(sizeMatcher);
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * equals the specified <code>size</code>.
     * <p/>
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(2))</pre>
     * 
     * @param size
     *     the length that an examined array must have for a positive match
     */
    @Factory
    public static <E> Matcher<E[]> arrayWithSize(int size) {
        return arrayWithSize(equalTo(size));
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * is zero.
     * <p/>
     * For example:
     * <pre>assertThat(new String[0], emptyArray())</pre>
     * 
     */
    @Factory
    public static <E> Matcher<E[]> emptyArray() {
        Matcher<E[]> isEmpty = arrayWithSize(0);
        return describedAs("an empty array", isEmpty);
    }
}
