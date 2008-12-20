package org.hamcrest;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.Matchers.equalTo;
import junit.framework.TestCase;

public class FeatureMatcherTest extends TestCase {
  private final FeatureMatcher<Thingy, String> resultMatcher = resultMatcher();

    public void testMatchesPartOfAnObject() {
      assertMatches("feature", resultMatcher, new Thingy("bar"));
      assertDescription("Thingy with result \"bar\"", resultMatcher);
    }

    public void testMismatchesPartOfAnObject() {
      assertMismatchDescription("result was \"foo\"", resultMatcher, new Thingy("foo"));
    }
 
    public void testDoesNotThrowNullPointerException() {
      assertMismatchDescription("was null", resultMatcher, null);
   }

   public void testDoesNotThrowClassCastException() {
       resultMatcher.matches(new ShouldNotMatch());
       StringDescription mismatchDescription = new StringDescription(); 
       resultMatcher.describeMismatch(new ShouldNotMatch(), mismatchDescription);
       assertEquals("was <ShouldNotMatch>", mismatchDescription.toString());
   }

  public static class Thingy {
    private final String result;

    public Thingy(String result) {
      this.result = result;
    }

    public String getResult() {
      return result;
    }
  }

  public static class ShouldNotMatch {
    @Override public String toString() { return "ShouldNotMatch"; }
  } 
  
  private static FeatureMatcher<Thingy, String> resultMatcher() {
    return new FeatureMatcher<Thingy, String>(equalTo("bar"), "Thingy with result", "result") {
      @Override
      public String featureValueOf(Thingy actual) {
        return actual.getResult();
      }
    };
  }

}
