package org.hamcrest.core;
import java.util.Iterator;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static java.util.Objects.requireNonNull;

public class TraversedAs<E> extends BaseMatcher<E> {
  
  private final Iterable<E> expected;
  
  public TraversedAs(Iterable<E> expected) {
    this.expected = requireNonNull(expected, "expected cannot be null");
  }

  public static <E> Matcher<E> traversedAs(Iterable<E> expected) {
    return new TraversedAs<E>(expected);
  }

  @Override
  public boolean matches(Object item) {
    Iterable<E> actual = (Iterable<E>) item;
    Iterator<E> expectedIt = expected.iterator(), actualIt = actual.iterator();
    while (expectedIt.hasNext()) {
      E expectedElem = expectedIt.next();
      E actualElem = actualIt.next();
      if (!expectedElem.equals(actualElem)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void describeTo(Description description) {
    // TODO Auto-generated method stub
    
  }

  
}
