package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * A matcher for arrays that matches when each item in the examined array satisfies the
 * corresponding matcher in the specified list of matchers.
 *
 * @param <E> the collection element type
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
public class ArrayAsIterableMatcher<E> extends TypeSafeMatcher<E[]> {

  /**
   * The matchers to match iterable against
   */
  protected final TypeSafeDiagnosingMatcher<Iterable<? extends E>> iterableMatcher;

  private final String message;

  /**
   * The matchers to match items against
   */
  protected final Collection<Matcher<? super E>> matchers;


  /**
   * Constructor, best called from {@link ArrayMatching#arrayContainingInAnyOrder(Matcher[])}.
   * @param iterableMatcher the iterable matchers
   * @param matchers the matchers
   * @param message the description of this matcher
   * @see ArrayMatching#arrayContainingInAnyOrder(Matcher[])
   */
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
