/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

public class Matchers {

    /**
     * Temporary Matcher... don't use this.. it will go away!
     */
    public static Matcher<String> isTwoXs() {
        return new Matcher<String>() {
            public boolean match(String string) {
                return string.equals("xx");
            }

            public void describeTo(Description description) {
                description.appendText("Two x's");
            }
        };
    }
}
