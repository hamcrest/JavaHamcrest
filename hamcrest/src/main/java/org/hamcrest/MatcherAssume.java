package org.hamcrest;

import org.opentest4j.TestAbortedException;

public final class MatcherAssume {

    private MatcherAssume() {
    }

    public static <T> void assumeThat(T assumption, Matcher<? super T> matcher) {
        assumeThat("", assumption, matcher);
    }

    public static <T> void assumeThat(String message, T assumption, Matcher<? super T> matcher) {
        if (!matcher.matches(assumption)) {
            throwTestAbortedException(message);
        }
    }

    private static void throwTestAbortedException(String message) {
        throw new TestAbortedException(isNotBlank(message) ? "Assumption failed: " + message : "Assumption failed");
    }

    private static boolean isNotBlank(String string) {
        return string != null && !string.trim().isEmpty();
    }
}
