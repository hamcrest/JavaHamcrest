package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
public class ArrayAsIterableMatcher<E> extends TypeSafeMatcher<E[]> {
  protected final TypeSafeDiagnosingMatcher<Iterable<? extends E>> iterableMatcher;
  private final String message;
  protected final Collection<Matcher<? super E>> matchers;

  public ArrayAsIterableMatcher(
        TypeSafeDiagnosingMatcher<Iterable<? extends E>> iterableMatcher,
        Collection<Matcher<? super E>> matchers,
        String message)
  {
    this.matchers = matchers;
    this.iterableMatcher = iterableMatcher;
    this.message = message;
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
      description.appendList("[", ", ", "]", matchers)
          .appendText(" ").appendText(message);
  }
}
