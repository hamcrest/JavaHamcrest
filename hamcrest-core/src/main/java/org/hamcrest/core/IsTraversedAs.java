package org.hamcrest.core;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static java.util.Arrays.asList;

public class IsTraversedAs<E> extends TypeSafeMatcher<Iterable<E>> {
  
  public static <E> Matcher<Iterable<E>> traversedAs(Iterable<E> expected) {
    return new IsTraversedAs<E>(expected);
  }
  
  @SafeVarargs
  public static <E> Matcher<Iterable<E>> traversedAs(E... expected) {
    return new IsTraversedAs<E>(asList(expected));
  }

  private final Iterable<E> expected;

  public IsTraversedAs(Iterable<E> expected) {
    this.expected = requireNonNull(expected, "expected cannot be null");
  }


  @Override
  public void describeTo(Description description) {
    description.appendValue(expected);
  }

  @Override
  protected boolean matchesSafely(Iterable<E> actual) {
    Iterator<E> expectedIt = expected.iterator(), actualIt = actual.iterator();
    while (expectedIt.hasNext()) {
      if (!actualIt.hasNext()) {
        return false;
      }
      E expectedElem = expectedIt.next();
      E actualElem = actualIt.next();
      if (!Objects.equals(expectedElem, actualElem)) {
        return false;
      }
    }
    return !actualIt.hasNext();
  }

}
