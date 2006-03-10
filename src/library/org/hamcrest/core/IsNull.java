/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.hamcrest.Description;


/**
 * Is the value null?
 */
public class IsNull implements Matcher {
    public boolean match(Object o) {
        return o == null;
    }

    public void describeTo(Description description) {
        description.append("null");
    }
}

