package test;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public static class MatcherWithNestedClass {

    @Factory
    public static SpecificMatcher firstMethod() {
        return null;
    }

    public static class SpecificMatcher extends Matcher {
    }
}
