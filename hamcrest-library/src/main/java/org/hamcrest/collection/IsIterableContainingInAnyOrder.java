package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

    @Factory
    public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(final Matcher<? super E> item) {
        return containsInAnyOrder(new ArrayList<Matcher<? super E>>(Arrays.asList(item)));
    }

    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... matchers) {
        return containsInAnyOrder(Arrays.asList(matchers));
    }

    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        for (T item : items) {
            matchers.add(equalTo(item));
        }
        
        return new IsIterableContainingInAnyOrder<T>(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> matchers) {
        return new IsIterableContainingInAnyOrder<T>(matchers);
    }
}

