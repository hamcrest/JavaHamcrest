package test;

import java.util.Collection;
import java.util.Set;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public static class ParameterizedMatchers {

    @Factory
    public static Matcher<String> withParam(String someString, int[] numbers, Collection<Object> things) {
        return null;
    }

    @Factory
    public static Matcher<String> withArray(String[] array) {
        return null;
    }

    @Factory
    public static Matcher<String> withVarArgs(String... things) {
        return null;
    }

    @Factory
    public static Matcher<String> withGenerifiedParam(Collection<? extends Comparable<String>> things, Set<String[]>[] x) {
        return null;
    }

}
