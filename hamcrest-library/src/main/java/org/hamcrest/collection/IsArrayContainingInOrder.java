package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsArrayContainingInOrder<E> extends TypeSafeMatcher<E[]> {
    private final Collection<Matcher<? super E>> matchers;
    private final IsIterableContainingInOrder<E> iterableMatcher;

    public IsArrayContainingInOrder(List<Matcher<? super E>> matchers) {
        this.iterableMatcher = new IsIterableContainingInOrder<E>(matchers);
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(E[] item) {
        return iterableMatcher.matches(asList(item));
    }
    
    @Override
    public void describeMismatchSafely(E[] item, Description mismatchDescription) {
      iterableMatcher.describeMismatch(asList(item), mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("[", ", ", "]", matchers);
    }

    /**
     * Creates a matcher for arrays that matcheswhen each item in the examined array is
     * logically equal to the corresponding item in the specified items.  For a positive match,
     * the examined array must be of the same length as the number of specified items.
     * <p/>
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains("foo", "bar"))</pre>
     * 
     * @param items
     *     the items that must equal the items within an examined array
     */
    @Factory
    public static <E> Matcher<E[]> arrayContaining(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }
        return arrayContaining(matchers);
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array satisfies the
     * corresponding matcher in the specified matchers.  For a positive match, the examined array
     * must be of the same length as the number of specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains(equalTo("foo"), equalTo("bar")))</pre>
     * 
     * @param itemMatchers
     *     the matchers that must be satisfied by the items in the examined array
     */
    @Factory
    public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... itemMatchers) {
        return arrayContaining(asList(itemMatchers));
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array satisfies the
     * corresponding matcher in the specified list of matchers.  For a positive match, the examined array
     * must be of the same length as the specified list of matchers.
     * <p/>
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the corresponding item in an examined array
     */
    @Factory
    public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> itemMatchers) {
        return new IsArrayContainingInOrder<E>(itemMatchers);
    }
}
