package org.hamcrest.internal;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assumptions;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * This class provides assumption violations compatible with JUnit 5.
 */
class JUnit5AssumptionProvider extends AssumptionProvider {

    static {
        // Trigger NoClassDefFoundError when JUnit 5 not on classpath.
        Assumptions.class.getName();
    }

    @Override
    public <T> void assumeThat(String message, T actual, Matcher<T> matcher) {
        if (stackTraceContains("org.junit.runners.", "org.junit.jupiter.")) {
            assumeTrue(matcher.matches(actual), message);
        }
    }
}
