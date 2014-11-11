package test;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

@SuppressWarnings("rawtypes")
public static class AnotherMatcher<T> implements Matcher<T> {
    @Override public void describeTo(Description description) { }
    @Override public boolean matches(Object item) { return false; }
    @Override public void describeMismatch(Object item, Description mismatchDescription) { }
    @Override @Deprecated public void _dont_implement_Matcher___instead_extend_BaseMatcher_() { }
    @Factory public static AnotherMatcher matcher3() { return null; }
}
