package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
public class TypeSafeDiagnosingMatcherTest {

  private final TypeSafeDiagnosingMatcher matcher = new TypeSafeDiagnosingMatcher<String>() {
    @Override
    protected boolean matchesSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("mismatching");
      return false;
    }

    @Override
    public void describeTo(Description description) { }
  };


  @Test public void
  describesMismatches() {
    assertMismatchDescription("was null", matcher, null);
    assertMismatchDescription("was Character \"c\"", matcher, 'c');
    assertMismatchDescription("mismatching", matcher, "other");
  }
}
