package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Iterator;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class IsIterableWithSize<I extends Iterable<?>> extends FeatureMatcher<I, Integer> {

    public IsIterableWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an iterable with size", "iterable size");
    }
    

    @Override
    protected Integer featureValueOf(I actual) {
      int size = 0;
      for (Iterator<?> iterator = actual.iterator(); iterator.hasNext(); iterator.next()) {
        size++;
      }
      return size;
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that satisfies the specified
     * matcher.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(equalTo(2)))</pre>
     * 
     * @param sizeMatcher
     *     a matcher for the number of items that should be yielded by an examined {@link Iterable}
     */
    @Factory
    public static <I extends Iterable<?>> Matcher<I> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsIterableWithSize<I>(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that is equal to the specified
     * <code>size</code> argument.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(2))</pre>
     * 
     * @param size
     *     the number of items that should be yielded by an examined {@link Iterable}
     */
    @Factory
    public static <I extends Iterable<?>> Matcher<I> iterableWithSize(int size) {
        return iterableWithSize(equalTo(size));
    }
}
