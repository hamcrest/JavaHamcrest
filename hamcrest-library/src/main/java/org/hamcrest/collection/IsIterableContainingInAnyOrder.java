package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingInAnyOrder<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final Collection<Matcher<? super T>> matchers;

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }
    
    @Override
    protected boolean matchesSafely(Iterable<? extends T> items, Description mismatchDescription) {
      Matching<T> matching = new Matching<T>(matchers, mismatchDescription);
      for (T item : items) {
        if (! matching.matches(item)) {
          return false;
        }
      }
      
      return matching.isFinished(items);
    }
    
    @Override
    public void describeTo(Description description) {
      description.appendText("iterable over ")
          .appendList("[", ", ", "]", matchers)
          .appendText(" in any order");
    }

    private static class Matching<S> {
      private final Collection<Matcher<? super S>> matchers;
      private final Description mismatchDescription;

      public Matching(Collection<Matcher<? super S>> matchers, Description mismatchDescription) {
        this.matchers = new ArrayList<Matcher<? super S>>(matchers);
        this.mismatchDescription = mismatchDescription;
      }
      
      public boolean matches(S item) {
        return isNotSurplus(item) && isMatched(item);
      }

      public boolean isFinished(Iterable<? extends S> items) {
        if (matchers.isEmpty()) {
          return true;
        }
        mismatchDescription
          .appendText("No item matches: ").appendList("", ", ", "", matchers)
          .appendText(" in ").appendValueList("[", ", ", "]", items);
        return false;
      }
      
      private boolean isNotSurplus(S item) {
        if (matchers.isEmpty()) {
          mismatchDescription.appendText("Not matched: ").appendValue(item);
          return false;
        }
        return true;
      }

      private boolean isMatched(S item) {
        for (Matcher<? super S>  matcher : matchers) {
          if (matcher.matches(item)) {
            matchers.remove(matcher);
            return true;
          }
        }
        mismatchDescription.appendText("Not matched: ").appendValue(item);
        return false;
      }

    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a single item that satisfies the specified matcher.
     * For a positive match, the examined iterable must only yield one item.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo"), containsInAnyOrder(equalTo("foo")))</pre>
     * 
     * @deprecated use contains(Matcher<? super E> itemMatcher) instead
     * 
     * @param itemMatcher
     *     the matcher that must be satisfied by the single item provided by an
     *     examined {@link Iterable}
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    @Factory
    public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(final Matcher<? super E> itemMatcher) {
        return containsInAnyOrder(new ArrayList<Matcher<? super E>>(asList(itemMatcher)));
    }

    /**
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified matchers.  For a positive match, the examined iterable must be of the same
     * length as the number of specified matchers.
     * <p/>
     * N.B. each of the specified matchers will only be used once during a given examination, so be
     * careful when specifying matchers that may be satisfied by more than one entry in an examined
     * iterable.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
        return containsInAnyOrder(Arrays.asList(itemMatchers));
    }

    /**
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each logically equal to one item
     * anywhere in the specified items. For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * <p/>
     * N.B. each of the specified items will only be used once during a given examination, so be
     * careful when specifying items that may be equal to more than one entry in an examined
     * iterable.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder("bar", "foo"))</pre>
     * 
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable} in any order
     */
    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        for (T item : items) {
            matchers.add(equalTo(item));
        }
        
        return new IsIterableContainingInAnyOrder<T>(matchers);
    }

    /**
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified collection of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified collection of matchers.
     * <p/>
     * N.B. each matcher in the specified collection will only be used once during a given
     * examination, so be careful when specifying matchers that may be satisfied by more than
     * one entry in an examined iterable.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return new IsIterableContainingInAnyOrder<T>(itemMatchers);
    }
}

