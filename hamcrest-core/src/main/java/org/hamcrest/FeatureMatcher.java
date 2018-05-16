package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

/**
 * Supporting class for matching a feature of an object. Implement <code>featureValueOf()</code>
 * in a subclass to pull out the feature to be matched against. 
 *
 * @param <T> The type of the object to be matched
 * @param <U> The type of the feature to be matched
 */
public abstract class FeatureMatcher<T, U> extends TypeSafeDiagnosingMatcher<T> {
  private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0); 
  private final Matcher<? super U> subMatcher;
  private final String featureDescription;
  private final String featureName;
  
  /**
   * Constructor
   * @param subMatcher The matcher to apply to the feature
   * @param featureDescription Descriptive text to use in describeTo
   * @param featureName Identifying text for mismatch message
   */
  public FeatureMatcher(Matcher<? super U> subMatcher, String featureDescription, String featureName) {
    super(TYPE_FINDER);
    this.subMatcher = subMatcher;
    this.featureDescription = featureDescription;
    this.featureName = featureName;
  }
  
  /**
   * Implement this to extract the interesting feature.
   * @param actual the target object
   * @return the feature to be matched
   */
  protected abstract U featureValueOf(T actual);

  @Override
  protected boolean matchesSafely(T actual, Description mismatch) {
    final U featureValue = featureValueOf(actual);
    if (!subMatcher.matches(featureValue)) {
      mismatch.appendText(featureName).appendText(" ");
      subMatcher.describeMismatch(featureValue, mismatch);
      return false;
    }
    return true;
  }
      
  @Override
  public final void describeTo(Description description) {
    description.appendText(featureDescription).appendText(" ")
               .appendDescriptionOf(subMatcher);
  }

  public static <T, U> FeatureMatcher<T, U> ofFunction(final Mapper<T, U> mapper, Matcher<? super U> matcher, String featureDescription, String featureName) {
  	return new FeatureMatcher<T, U>(matcher, featureDescription, featureName) {
	  @Override
	  protected U featureValueOf(T actual) {
	    return mapper.map(actual);
	  }
	};
  }

  // TODO java 8 @FunctionalInterface
  public interface Mapper<T, U> {
  	U map(T t);
  }
}
