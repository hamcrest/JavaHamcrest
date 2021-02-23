package org.hamcrest.internal;

import org.hamcrest.Matcher;
import org.junit.Assume;

/**
 * This class provides assumption violations compatible with JUnit 4.
 */
class JUnit4AssumptionProvider extends AssumptionProvider {

    static {
        // Trigger NoClassDefFoundError when JUnit 4 not on classpath.
        Assume.class.getName();
    }

    @Override
    public <T> void assumeThat(String message, T actual, Matcher<T> matcher) {
        if (stackTraceContains("org.junit.jupiter.", "org.junit.runners.")) {
            Assume.assumeThat(message, actual, matcher);
        }
    }
}
