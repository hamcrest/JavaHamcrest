package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsIterableContainingInOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInOrder(List<Matcher<? super E>> matchers) {
      this.matchers = matchers;
    }
    
    @Override
    protected boolean matchesSafely(Iterable<E> iterable, Description mismatchDescription) {
      MatchSeries<E> matchSeries = new MatchSeries<E>(matchers, mismatchDescription);
      for (E item : iterable) {
        if (! matchSeries.matches(item)) {
          return false;
        }
      }
      
      return matchSeries.isFinished();
    }

    public void describeTo(Description description) {
        description.appendText("iterable over ").appendList("[", ", ", "]", matchers);
    }

    private static class MatchSeries<F> {
      public final List<Matcher<? super F>> matchers;
      private final Description mismatchDescription;
      public int nextMatchIx = 0;
      
      public MatchSeries(List<Matcher<? super F>> matchers, Description mismatchDescription) {
        this.mismatchDescription = mismatchDescription;
        if (matchers.isEmpty()) {
          throw new IllegalArgumentException("Should specify at least one expected element");
        }
        this.matchers = matchers;
      }

      public boolean matches(F item) {
        return isNotSurplus(item) && isMatched(item);
      }

      public boolean isFinished() {
        if (nextMatchIx < matchers.size()) {
          mismatchDescription.appendText("No item: ").appendDescriptionOf(matchers.get(nextMatchIx));
          return false;
        }
        return true;
      }

      private boolean isMatched(F item) {
        Matcher<? super F> matcher = matchers.get(nextMatchIx);
        if (!matcher.matches(item)) {
          describeMismatch(matcher, item);
          return false;
        }
        nextMatchIx++;
        return true;
      }

      private boolean isNotSurplus(F item) {
        if (matchers.size() <= nextMatchIx) {
          mismatchDescription.appendText("Not matched: ").appendValue(item);
          return false;          
        }
        return true;
      }
      
      private void describeMismatch(Matcher<? super F> matcher, F item) {
        mismatchDescription.appendText("item " + nextMatchIx + ": ");
        matcher.describeMismatch(item, mismatchDescription);
      }
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second, E third) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second, E third, E forth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second, E third, E forth, E fifth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(E first, E second, E third, E forth, E fifth, E sixth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        matchers.add(equalTo(sixth));
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(final Matcher<E> item) {
        return contains(new ArrayList<Matcher<? super E>>() {{ add(item); }});
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<? super E>... items) {
        return contains(Arrays.asList(items));
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth, Matcher<? super E> fifth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth, Matcher<? super E> fifth, Matcher<? super E> sixth) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        matchers.add(sixth);
        return contains(matchers);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> contains(List<Matcher<? super E>> contents) {
        return new IsIterableContainingInOrder<E>(contents);
    }
}
