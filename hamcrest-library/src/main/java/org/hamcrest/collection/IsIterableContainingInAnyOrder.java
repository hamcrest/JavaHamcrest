package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsIterableContainingInAnyOrder<T> extends TypeSafeDiagnosingMatcher<Iterable<T>> {
    private final Collection<Matcher<? super T>> matchers;

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }
    
    @Override
    protected boolean matchesSafely(Iterable<T> items, Description mismatchDescription) {
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
        this.matchers = new ArrayList<Matcher<? super S>>(matchers);;
        this.mismatchDescription = mismatchDescription;
      }
      
      public boolean matches(S item) {
        return isNotSurplus(item) && isMatched(item);
      }

      public boolean isFinished(Iterable<S> items) {
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
    public static <E> Matcher<Iterable<E>> containsInAnyOrder(final Matcher<E> item) {
        return containsInAnyOrder(new ArrayList<Matcher<? super E>>() {{ add(item); }});
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<? super T>... matchers) {
        return containsInAnyOrder(Arrays.asList(matchers));
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T... items) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        for (T item : items) {
            matchers.add(equalTo(item));
        }
        return new IsIterableContainingInAnyOrder<T>(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second, T third) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second, T third, T forth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second, T third, T forth, T fifth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(T first, T second, T third, T forth, T fifth, T sixth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(equalTo(first));
        matchers.add(equalTo(second));
        matchers.add(equalTo(third));
        matchers.add(equalTo(forth));
        matchers.add(equalTo(fifth));
        matchers.add(equalTo(sixth));
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> forth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> forth, Matcher<? super T> fifth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> forth, Matcher<? super T> fifth,  Matcher<? super T> sixth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(forth);
        matchers.add(fifth);
        matchers.add(sixth);
        return containsInAnyOrder(matchers);
    }

    @Factory
    public static <T> Matcher<Iterable<T>> containsInAnyOrder(Collection<Matcher<? super T>> matchers) {
        return new IsIterableContainingInAnyOrder<T>(matchers);
    }
}

