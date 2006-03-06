/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.junit;

import org.hamcrest.Matcher;
import junit.framework.Assert;

public class MatcherAssert extends Assert {

    public void assertThat(Object actual, Matcher matcher) {
        if (!matcher.match(actual)) {
            StringBuffer message = new StringBuffer("\nExpected: ");
            matcher.describeTo(message);
            message.append("\n    got : ").append(actual).append('\n');
            fail(message.toString());
        }
    }

    public void assertThat(boolean actual, Matcher matcher) {
        assertThat(actual ? Boolean.TRUE : Boolean.FALSE, matcher);
    }

    public void assertThat(byte actual, Matcher matcher) {
        assertThat(new Byte(actual), matcher);
    }

    public void assertThat(short actual, Matcher matcher) {
        assertThat(new Short(actual), matcher);
    }

    public void assertThat(char actual, Matcher matcher) {
        assertThat(new Character(actual), matcher);
    }

    public void assertThat(int actual, Matcher matcher) {
        assertThat(new Integer(actual), matcher);
    }

    public void assertThat(long actual, Matcher matcher) {
        assertThat(new Long(actual), matcher);
    }

    public void assertThat(float actual, Matcher matcher) {
        assertThat(new Float(actual), matcher);
    }

    public void assertThat(double actual, Matcher matcher) {
        assertThat(new Double(actual), matcher);
    }

}
