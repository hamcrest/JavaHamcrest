/*  Copyright (c) 2000-2010 hamcrest.org
 */
package org.hamcrest.examples.junit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

/**
 * Demonstrates how HamCrest matchers can be used with assertThat()
 * using JUnit 4.x.
 *
 * @author Joe Walnes
 */
public class ExampleWithAssertThat {

    @Test
    public void usingAssertThat() {
        assertThat("xx", is("xx"));
        assertThat("yy", is(not("xx")));
        assertThat("i like cheese", containsString("cheese"));
    }

    /**
     * Allow JUnit 4 test to be run under JUnit 3.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(ExampleWithAssertThat.class);
    }
    
    public static class ComplicatedClass {
      private int firstNumber = 23;
      private int secondNumber = 45;
      private String someText = "This is useful text";
      
      public String whichOne(boolean first) {
        return someText + (first ? firstNumber : secondNumber);
      }
    }
    
    @Test
    public void showMismatch() {
      ComplicatedClass complicated = new ComplicatedClass();
      
      assertThat(complicated, shouldBe("the wrong thing"));
    }

    private static Matcher<ComplicatedClass> shouldBe(final String string) {
      return new TypeSafeMatcher<ComplicatedClass>() {
        @Override
        public void describeTo(Description description) { } // no op
        @Override
        public boolean matchesSafely(ComplicatedClass item) { return false; }
        @Override
        public void describeMismatchSafely(ComplicatedClass item, Description mismatchDescription) {
          mismatchDescription.appendText(string);
        } 
      };
    }

}
