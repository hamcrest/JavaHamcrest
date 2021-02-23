package org.hamcrest.internal;

import org.hamcrest.Matcher;

/**
 * The default (fallback) assumption provider throws {@link AssertionError}, by lack of anything better.
 */
class AssertionAssumptionProvider extends AssumptionProvider {

    @Override
    public <T> void assumeThat(String message, T actual, Matcher<T> matcher) {
        // Java only knows assertions, not assumptions.
        // By lack of anything better, we treat an assumption as an assertion by default.
        // Alternatively, we could just do nothing, but that would result in tests failing for the wrong reason,
        // because their assumptions would silently fail, which is worse.
        if (!matcher.matches(actual)) {
            throw new AssertionError(isNotBlank(message) ? "Assumption failed: " + message : "Assumption failed");
        }
    }

    private static boolean isNotBlank(String string) {
        return string != null && !string.trim().isEmpty();
    }
}
