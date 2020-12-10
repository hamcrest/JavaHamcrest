package org.hamcrest.collection;

import org.hamcrest.Matcher;

import java.util.Collection;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if collection size satisfies a nested matcher.
 */
public class IsCollectionWithSize<E> extends SizeMatcher<Collection<? extends E>, E> {
    public IsCollectionWithSize(Matcher<? super Integer> sizeMatcher) {
      super(sizeMatcher, "collection");
    }

    @Override
    protected Integer featureValueOf(Collection<? extends E> actual) {
      return actual.size();
    }

    @Override
    protected Iterable<? extends E> actualValues(Collection<? extends E> actual) {
      return actual;
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
     * a value that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(equalTo(2)))</pre>
     * 
     * @param sizeMatcher
     *     a matcher for the size of an examined {@link java.util.Collection}
     */
    public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> sizeMatcher) {
        return new IsCollectionWithSize<E>(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
     * a value equal to the specified <code>size</code>.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(2))</pre>
     * 
     * @param size
     *     the expected size of an examined {@link java.util.Collection}
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <E> Matcher<Collection<? extends E>> hasSize(int size) {
        return (Matcher)IsCollectionWithSize.hasSize(equalTo(size));
    }

}
