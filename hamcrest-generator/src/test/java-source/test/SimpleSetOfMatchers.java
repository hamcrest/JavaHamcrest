package test;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public static class SimpleSetOfMatchers {

    @Factory
    public static Matcher<String> firstMethod() {
        return null;
    }

    @Factory
    public static Matcher<String> secondMethod() {
        return null;
    }

}
