package org.hamcrest.test;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.test.MatcherAssertions.assertNullSafe;

public abstract class AbstractMatcherTest {
    /**
     * Create an instance of the Matcher so some generic safety-net tests can be run on it.
     */
    protected abstract Matcher<?> createMatcher();

    @Test
    public void testIsNullSafe() {
        assertNullSafe(createMatcher());
    }

    @Test
    public void testCopesWithUnknownTypes() {
        createMatcher().matches(new UnknownType());
    }
}
