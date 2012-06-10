package org.hamcrest.collection;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.Collection;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if collection size satisfies a nested matcher.
 */
public class IsCollectionWithSize<E> extends FeatureMatcher<Collection<? extends E>, Integer> {
    public IsCollectionWithSize(Matcher<? super Integer> sizeMatcher) {
      super(sizeMatcher, "a collection with size", "collection size");
    }

    @Override
    protected Integer featureValueOf(Collection<? extends E> actual) {
      return actual.size();
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
     * a value that satisfies the specified matcher.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(equalTo(2)))</pre>
     * 
     * @param sizeMatcher
     *     a matcher for the size of an examined {@link java.util.Collection}
     */
    @Factory
    public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> sizeMatcher) {
        return new IsCollectionWithSize<E>(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
     * a value equal to the specified <code>size</code>.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(2))</pre>
     * 
     * @param size
     *     the expected size of an examined {@link java.util.Collection}
     */
    @Factory
    public static <E> Matcher<Collection<? extends E>> hasSize(int size) {
        Matcher<? super Integer> matcher = equalTo(size);
        return IsCollectionWithSize.<E>hasSize(matcher);
    }

}
