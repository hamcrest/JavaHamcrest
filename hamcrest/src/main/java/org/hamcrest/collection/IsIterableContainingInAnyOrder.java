package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.internal.InPlacePermutator;

import java.util.*;

import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingInAnyOrder<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final Collection<Matcher<? super T>> matchers;

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(final Iterable<? extends T> items, Description mismatchDescription) {
        final T[] itemsArray = toArray(items);
        @SuppressWarnings({"unchecked"})
        final Matcher<? super T>[] matchersArray = matchers.toArray(new Matcher[0]);

        if (itemsArray.length > matchersArray.length) {
            mismatchDescription.appendText("too many items: ").appendValueList("[", ", ", "]", items);
            return false;
        } else if (itemsArray.length < matchersArray.length) {
            mismatchDescription.appendText("not enough items: ").appendValueList("[", ", ", "]", items);
            return false;
        } else {
            boolean matchesSomePermutation = checkMatchersPermutations(matchersArray, itemsArray);
            if (!matchesSomePermutation) {
                mismatchDescription.appendText("no permutation of the matchers matched the items sequence");
            }
            return matchesSomePermutation;
        }
    }

    private boolean checkMatchersPermutations(final Matcher<? super T>[] matchersArray, final T[] itemsArray) {
        InPlacePermutator permutator = new InPlacePermutator<Matcher<? super T>>(matchersArray) {
            @Override
            protected boolean handlePermutation() {
                return permutationMatches(matchersArray, itemsArray);
            }
        };
        return permutator.iteratePermutations();
    }

    @SuppressWarnings({"unchecked"})
    private static <U> U[] toArray(Iterable<? extends U> items) {
        ArrayList<U> arrayList = new ArrayList<>();
        for (U item : items) {
            arrayList.add(item);
        }
        return (U[]) arrayList.toArray();
    }

    private boolean permutationMatches(Matcher<? super T>[] matchersPermutation, T[] items) {
        for (int i = 0; i < items.length; i++) {
            if (!matchersPermutation[i].matches(items[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
      description.appendText("iterable with items ")
          .appendList("[", ", ", "]", matchers)
          .appendText(" in any order");
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified matchers.  For a positive match, the examined iterable must be of the same
     * length as the number of specified matchers.
     * </p>
     * <p>
     * N.B. each of the specified matchers will only be used once during a given examination, so be
     * careful when specifying matchers that may be satisfied by more than one entry in an examined
     * iterable.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
        return containsInAnyOrder(Arrays.asList(itemMatchers));
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each logically equal to one item
     * anywhere in the specified items. For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * </p>
     * <p>
     * N.B. each of the specified items will only be used once during a given examination, so be
     * careful when specifying items that may be equal to more than one entry in an examined
     * iterable.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder("bar", "foo"))</pre>
     * 
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable} in any order
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        List<Matcher<? super T>> matchers = new ArrayList<>();
        for (T item : items) {
            matchers.add(equalTo(item));
        }
        
        return new IsIterableContainingInAnyOrder<>(matchers);
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified collection of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified collection of matchers.
     * </p>
     * <p>
     * N.B. each matcher in the specified collection will only be used once during a given
     * examination, so be careful when specifying matchers that may be satisfied by more than
     * one entry in an examined iterable.
     * </p>
     * <p>For example:</p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return new IsIterableContainingInAnyOrder<>(itemMatchers);
    }


}

