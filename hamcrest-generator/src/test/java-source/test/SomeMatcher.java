package test;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

@SuppressWarnings("rawtypes")
public class SomeMatcher {
    @Factory public static Matcher matcher1() { return null; }
    @Factory public static Matcher matcher2() { return null; }
}
