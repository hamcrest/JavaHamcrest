/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest;

/**
 * Integration method for use with Java's <code>assert</code> keyword.
 * Example:
 * <pre>
 * assert that("Foo", startsWith("f"));
 * </pre>
 *
 * @author Neil Dunn
 */
public class JavaLangMatcherAssert {
    private JavaLangMatcherAssert() {};

    public static <T> boolean that(T argument, Matcher<? super T> matcher) {
        return matcher.matches(argument);
    }
}
