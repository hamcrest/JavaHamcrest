package org.hamcrest;

import org.hamcrest.internal.AssumptionProvider;

public final class MatcherAssume {

    private MatcherAssume() {
    }

    public static <T> void assumeThat(T actual, Matcher<? super T> matcher) {
        assumeThat("", actual, matcher);
    }

    public static <T> void assumeThat(String message, T actual, Matcher<? super T> matcher) {
        AssumptionProvider.getInstance().assumeThat(message, actual, matcher);
    }
}
