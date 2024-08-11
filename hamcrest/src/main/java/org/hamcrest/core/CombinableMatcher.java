package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;

/**
 * Allows matchers of the same type to be combined using
 * <code>either</code>/<code>or</code>, or
 * <code>both</code>/<code>and</code>.
 *
 * For example:
 *
 * <pre>{@code  import static org.hamcrest.core.CombinableMatcher.either;
 * import static org.hamcrest.core.CombinableMatcher.both;
 * import static org.hamcrest.Matchers.equalTo;
 * import static org.hamcrest.Matchers.not;
 *
 * Matcher<Integer> either_3_or_4 = either(equalTo(3)).or(equalTo(4));
 * Matcher<Integer> neither_3_nor_4 = both(not(equalTo(3))).and(not(equalTo(4)));}</pre>
 *
 * @param <T> the type of matcher being combined.
 * @see #either(Matcher)
 * @see #both(Matcher)
 */
public class CombinableMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

  private final Matcher<? super T> matcher;

  /**
   * Constructor, best called from <code>either</code> or <code>both</code>.
   * @see #either(Matcher)
   * @see #both(Matcher) 
   */
  @SuppressWarnings("doclint")
  public CombinableMatcher(Matcher<? super T> matcher) {
    this.matcher = matcher;
  }

  @Override
  protected boolean matchesSafely(T item, Description mismatch) {
    if (!matcher.matches(item)) {
      matcher.describeMismatch(item, mismatch);
      return false;
    }
    return true;
  }

  @Override
  public void describeTo(Description description) {
    description.appendDescriptionOf(matcher);
  }

  /**
   * Specify the second matcher in a <code>CombinableMatcher</code> pair.
   * @param other the second matcher
   * @return the combined matcher
   */
  public CombinableMatcher<T> and(Matcher<? super T> other) {
    return new CombinableMatcher<>(new AllOf<>(templatedListWith(other)));
  }

  /**
   * Specify the second matcher in a <code>CombinableMatcher</code> pair.
   * @param other the second matcher
   * @return the combined matcher
   */
  public CombinableMatcher<T> or(Matcher<? super T> other) {
    return new CombinableMatcher<>(new AnyOf<>(templatedListWith(other)));
  }

  private ArrayList<Matcher<? super T>> templatedListWith(Matcher<? super T> other) {
    ArrayList<Matcher<? super T>> matchers = new ArrayList<>();
    matchers.add(matcher);
    matchers.add(other);
    return matchers;
  }

  /**
   * Creates a matcher that matches when both of the specified matchers match the examined object.
   * For example:
   * <pre>assertThat("fab", both(containsString("a")).and(containsString("b")))</pre>
   *
   * @param <LHS>
   *     the matcher type.
   * @param matcher
   *     the matcher to combine, and both must pass.
   * @return The matcher.
   */
  public static <LHS> CombinableBothMatcher<LHS> both(Matcher<? super LHS> matcher) {
    return new CombinableBothMatcher<>(matcher);
  }

  /**
   * Allows syntactic sugar of using <code>both</code> and <code>and</code>.
   * @param <X> the combined matcher type
   * @see #both(Matcher)
   * @see #and(Matcher)
   */
  public static final class CombinableBothMatcher<X> {
    private final Matcher<? super X> first;

    @SuppressWarnings("doclint")
    public CombinableBothMatcher(Matcher<? super X> matcher) {
        this.first = matcher;
    }

    @SuppressWarnings("doclint")
    public CombinableMatcher<X> and(Matcher<? super X> other) {
      return new CombinableMatcher(first).and(other);
    }
  }

  /**
   * Creates a matcher that matches when either of the specified matchers match the examined object.
   * For example:
   * <pre>assertThat("fan", either(containsString("a")).or(containsString("b")))</pre>
   *
   * @param <LHS>
   *     the matcher type.
   * @param matcher
   *     the matcher to combine, and either must pass.
   * @return The matcher.
   */
  public static <LHS> CombinableEitherMatcher<LHS> either(Matcher<? super LHS> matcher) {
    return new CombinableEitherMatcher<>(matcher);
  }

  /**
   * Allows syntactic sugar of using <code>either</code> and <code>or</code>.
   * @param <X> the combined matcher type
   * @see #either(Matcher)
   * @see #or(Matcher)
   */
  public static final class CombinableEitherMatcher<X> {
    private final Matcher<? super X> first;

    @SuppressWarnings("doclint")
    public CombinableEitherMatcher(Matcher<? super X> matcher) {
        this.first = matcher;
    }

    @SuppressWarnings("doclint")
    public CombinableMatcher<X> or(Matcher<? super X> other) {
      return new CombinableMatcher(first).or(other);
    }
  }

}
