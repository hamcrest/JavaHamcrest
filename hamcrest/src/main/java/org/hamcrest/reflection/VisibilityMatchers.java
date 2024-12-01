package org.hamcrest.reflection;

import org.hamcrest.Matcher;

/**
 * Defines matchers that check the visibility of reflective objects like {@link java.lang.Class} or {@link java.lang.reflect.Method}.
 * {@code null} values never match, nor do normal objects; these simply do not match, without raising an Exception.
 *
 * @author JJ Brown
 */
public class VisibilityMatchers {
    // Each matcher is stateless and can match any type, so the individual instances are only made once and stored here for re-use.
    private static final VisibilityMatcher<?> PUBLIC = new VisibilityMatcher<>(Visibility.PUBLIC);
    private static final VisibilityMatcher<?> PROTECTED = new VisibilityMatcher<>(Visibility.PROTECTED);
    private static final VisibilityMatcher<?> PACKAGE_PROTECTED = new VisibilityMatcher<>(Visibility.PACKAGE_PROTECTED);
    private static final VisibilityMatcher<?> PRIVATE = new VisibilityMatcher<>(Visibility.PRIVATE);

    /**
     * Matchers reflective elements that have public visibility.
     * Specifically, this matcher only matches elements marked with the keyword {@code public}.
     * <br>
     * This method matches {@link Class} objects or other {@link java.lang.reflect.Member reflective objects}
     * like {@link java.lang.reflect.Field} or {@link java.lang.reflect.Method} used in reflection.
     * Any other kind of object, or {@code null} values, do not match (but will not cause an Exception).
     *
     * @param <T> the type of the object being matched
     * @return a matcher that matches reflective elements with exactly the given level of visibility
     */
    @SuppressWarnings("unchecked")
    public static <T> Matcher<T> isPublic() {
        // Each matcher is stateless and can match any type (the generic <T> is for type safety at the use site),
        // so it's fine to cast the non-reifiable generic type here at runtime and re-use the same instance.
        return (Matcher<T>) PUBLIC;
    }

    /**
     * Matchers reflective elements that have protected visibility.
     * Specifically, this matcher only matches elements marked with the keyword {@code protected}; it does NOT match public or private elements.
     * <br>
     * This method matches {@link Class} objects or other {@link java.lang.reflect.Member reflective objects}
     * like {@link java.lang.reflect.Field} or {@link java.lang.reflect.Method} used in reflection.
     * Any other kind of object, or {@code null} values, do not match (but will not cause an Exception).
     *
     * @param <T> the type of the object being matched
     * @return a matcher that matches reflective elements with exactly the given level of visibility
     */
    @SuppressWarnings("unchecked")
    public static <T> Matcher<T> isProtected() {
        // Each matcher is stateless and can match any type (the generic <T> is for type safety at the use site),
        // so it's fine to cast the non-reifiable generic type here at runtime and re-use the same instance.
        return (Matcher<T>) PROTECTED;
    }

    /**
     * Matchers reflective elements that have package-protected visibility.
     * Specifically, this matcher only matches elements not marked with any of the visibility keywords {@code public}, {@code protected}, or {@code private}.
     * <br>
     * This method matches {@link Class} objects or other {@link java.lang.reflect.Member reflective objects}
     * like {@link java.lang.reflect.Field} or {@link java.lang.reflect.Method} used in reflection.
     * Any other kind of object, or {@code null} values, do not match (but will not cause an Exception).
     *
     * @param <T> the type of the object being matched
     * @return a matcher that matches reflective elements with exactly the given level of visibility
     */
    @SuppressWarnings("unchecked")
    public static <T> Matcher<T> isPackageProtected() {
        // Each matcher is stateless and can match any type (the generic <T> is for type safety at the use site),
        // so it's fine to cast the non-reifiable generic type here at runtime and re-use the same instance.
        return (Matcher<T>) PACKAGE_PROTECTED;
    }

    /**
     * Matchers reflective elements that have private visibility.
     * Specifically, this matcher only matches elements marked with the keyword {@code private}.
     * <br>
     * This method matches {@link Class} objects or other {@link java.lang.reflect.Member reflective objects}
     * like {@link java.lang.reflect.Field} or {@link java.lang.reflect.Method} used in reflection.
     * Any other kind of object, or {@code null} values, do not match (but will not cause an Exception).
     *
     * @param <T> the type of the object being matched
     * @return a matcher that matches reflective elements with exactly the given level of visibility
     */
    @SuppressWarnings("unchecked")
    public static <T> Matcher<T> isPrivate() {
        // Each matcher is stateless and can match any type (the generic <T> is for type safety at the use site),
        // so it's fine to cast the non-reifiable generic type here at runtime and re-use the same instance.
        return (Matcher<T>) PRIVATE;
    }
}