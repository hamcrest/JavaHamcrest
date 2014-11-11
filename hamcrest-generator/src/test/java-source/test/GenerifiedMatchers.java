package test;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public static class GenerifiedMatchers {

    @Factory
    public static Matcher<Comparator<String>> generifiedType() {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Factory
    public static Matcher noGenerifiedType() {
        return null;
    }

    @Factory
    public static Matcher<Map<? extends Set<Long>, Factory>> crazyType() {
        return null;
    }

}
