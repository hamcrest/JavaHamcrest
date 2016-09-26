package org.hamcrest;

/**
 * <p>
 * Utility class to format matcher descriptions.
 * </p>
 *
 * @since 2013-05-08
 */
public final class MatcherExecute {
    /**
     * Utility class. Don't instantiate.
     */
    private MatcherExecute() {
    }

    /**
     * <p>
     * Execute {@link Matcher#matches(Object)} and on failure execute the given fail strategy.
     * </p>
     *
     * @param actual       actual value to compare through matcher
     * @param matcher      matcher to use
     * @param failStrategy strategy to execute on failure
     * @param <T>          type of the value to compare
     * @see FailStrategy
     * @see AssertionErrorStrategy
     * @see #failWithAssertionError()
     */
    public static <T> void match(final T actual, final Matcher<? super T> matcher, final FailStrategy failStrategy) {
        match("", actual, matcher, failStrategy);
    }

    /**
     * <p>
     * Execute {@link Matcher#matches(Object)} and on failure execute the given fail strategy.
     * </p>
     *
     * @param reason       reason description; must not be {@code null}
     * @param actual       actual value to compare through matcher
     * @param matcher      matcher to use; must not be {@code null}
     * @param failStrategy strategy to execute on failure; must not be {@code null}
     * @param <T>          type of the value to compare
     * @see FailStrategy
     * @see AssertionErrorStrategy
     * @see #failWithAssertionError()
     */
    public static <T> void match(final String reason, final T actual, final Matcher<? super T> matcher, final FailStrategy failStrategy) {
        if (!matcher.matches(actual)) {
            final Description description = new StringDescription();
            description.appendText(reason)
                       .appendText("\nExpected: ")
                       .appendDescriptionOf(matcher)
                       .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);

            failStrategy.fail(description.toString());
        }
    }

    /**
     * <p>
     * Convenience method to use with static import.
     * </p>
     * <dl>
     * <dt><strong>Usage:</strong></dt>
     * <dd><pre>{@code
     * match(actual, equalTo(expected), failWithAssertionError());
     * }</pre></dd>
     * </dl>
     *
     * @return fail strategy throwing Assertion Errors
     */
    @SuppressWarnings("JavaDoc")
    public static FailStrategy failWithAssertionError() {
        return new AssertionErrorStrategy();
    }

    /**
     * <p>
     * Interface for fail strategies. Typical implementations will throw an exception when
     * {@link #fail(String)} is called.
     * </p>
     */
    public interface FailStrategy {
        /**
         * Strategy to fail. Expected to throw an exception with the given message.
         *
         * @param message failure message
         */
        void fail(String message);
    }

    /**
     * FailStrategy throwing exceptions of type {@link AssertionError}.
     *
     * @see #failWithAssertionError()
     */
    public static final class AssertionErrorStrategy implements FailStrategy {
        @Override
        public void fail(final String message) {
            throw new AssertionError(message);
        }
    }

}
