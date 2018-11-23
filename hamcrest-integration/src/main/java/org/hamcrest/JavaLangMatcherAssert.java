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
