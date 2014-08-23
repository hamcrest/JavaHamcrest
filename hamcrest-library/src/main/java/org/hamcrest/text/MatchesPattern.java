package org.hamcrest.text;

import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class MatchesPattern extends TypeSafeMatcher<String> {
    private final Pattern pattern;

    public MatchesPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    protected boolean matchesSafely(String item) {
        return pattern.matcher(item).matches();
    }

    @Override
    protected void describeMismatchSafely(String item, Description mismatchDescription) {
        // TODO Auto-generated method stub
        super.describeMismatchSafely(item, mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        // TODO Auto-generated method stub
    }
}
