package org.hamcrest.object;

import static org.junit.Assert.assertThat;

import java.util.regex.Pattern;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.text.MatchesPattern;

public class MatchesPatternTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return new MatchesPattern(Pattern.compile("."));
    }

    public void testMatchesExactMatch() {
        assertThat("a", new MatchesPattern(Pattern.compile("a")));
    }

    public void testDoesNotMatchesDifferentString() {
        assertDoesNotMatch("A different string does not match", new MatchesPattern(Pattern.compile("a")), "b");
    }

    public void testDoesNotMatchSubstring() {
        assertDoesNotMatch("A substring does not match", new MatchesPattern(Pattern.compile("a")), "ab");
    }
}
