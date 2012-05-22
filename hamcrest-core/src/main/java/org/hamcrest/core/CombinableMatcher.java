package org.hamcrest.core;

import org.hamcrest.*;

import java.util.ArrayList;

public class CombinableMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
  private final Matcher<? super T> matcher;

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

  public CombinableMatcher<T> and(Matcher<? super T> other) {
    return new CombinableMatcher<T>(new AllOf<T>(templatedListWith(other)));
  }

  public CombinableMatcher<T> or(Matcher<? super T> other) {
    return new CombinableMatcher<T>(new AnyOf<T>(templatedListWith(other)));
  }

  private ArrayList<Matcher<? super T>> templatedListWith(Matcher<? super T> other) {
    ArrayList<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
    matchers.add(matcher);
    matchers.add(other);
    return matchers;
  }

  /**
   * Creates a matcher that matches when both of the specified matchers match the examined object.
   * <p/>
   * For example:
   * <pre>assertThat("fab", both(containsString("a")).and(containsString("b")))</pre>
   */
  @Factory
  public static <LHS> CombinableBothMatcher<LHS> both(Matcher<? super LHS> matcher) {
    return new CombinableBothMatcher<LHS>(matcher);
  }
  
  public static final class CombinableBothMatcher<X> {
    private final Matcher<? super X> first;
    public CombinableBothMatcher(Matcher<? super X> matcher) {
        this.first = matcher;
    }
    public CombinableMatcher<X> and(Matcher<? super X> other) {
      return new CombinableMatcher<X>(first).and(other);
    }
  }

  /**
   * Creates a matcher that matches when either of the specified matchers match the examined object.
   * <p/>
   * For example:
   * <pre>assertThat("fan", either(containsString("a")).and(containsString("b")))</pre>
   */
  @Factory
  public static <LHS> CombinableEitherMatcher<LHS> either(Matcher<? super LHS> matcher) {
    return new CombinableEitherMatcher<LHS>(matcher);
  }
  
  public static final class CombinableEitherMatcher<X> {
    private final Matcher<? super X> first;
    public CombinableEitherMatcher(Matcher<? super X> matcher) {
        this.first = matcher;
    }
    public CombinableMatcher<X> or(Matcher<? super X> other) {
      return new CombinableMatcher<X>(first).or(other);
    }
  }
}