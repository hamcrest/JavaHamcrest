package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

/**
 * Base class for Matchers matching a feature on a collection-like object.
 * <p>
 * Provides insightful debugging information in case the collection does not match,
 * by printing the actual values in the mismatch description.
 *
 * @param <T> the type of the collection
 * @param <E> the type of the elements in the collection
 */
public abstract class SizeMatcher<T, E> extends FeatureMatcher<T, Integer> {

  public SizeMatcher(Matcher<? super Integer> sizeMatcher, String type) {
    super(sizeMatcher, indefiniteArticle(type) + " " + type + " with size", type + " size");
  }

  @Override
  protected abstract Integer featureValueOf(T actual);

  @Override
  protected boolean matchesSafely(T actual, Description mismatch) {
    boolean matchesSafely = super.matchesSafely(actual, mismatch);
    if (!matchesSafely) {
      mismatch.appendText(". Actual values: ");
      mismatch.appendValueList("[", ", ", "]", actualValues(actual));
    }
    return matchesSafely;
  }

  protected abstract Iterable<? extends E> actualValues(T actual);

  private static String indefiniteArticle(String string) {
    // a naive algorithm..
    switch (string.charAt(0)) {
      case 'a':
      case 'e':
      case 'i':
      case 'o':
      case 'u':
        return "an";
      default:
        return "a";
    }
  }
}
