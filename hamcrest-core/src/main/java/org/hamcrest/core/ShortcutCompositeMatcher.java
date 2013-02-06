package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A base class for composite matchers that perform multiple matches
 * in sequence and can stop matching as soon as one match succeedes or
 * fails.
 */
abstract class ShortcutCompositeMatcher<T> extends DiagnosingMatcher<T> {

    private final Iterable<Matcher<? super T>> matchers;
    private final boolean shortcut;
    private final String operator;
    private Class<T> parameterType;

    public ShortcutCompositeMatcher(Iterable<Matcher<? super T>> matchers, boolean shortcut, String operator) {
        this.matchers = matchers;
        this.shortcut = shortcut;
        this.operator = operator;
        this.parameterType = calculateParameterType(matchers);
    }

    /**
     * Best guess for our parameter type is the most specific matcher parameter type
     */
    @SuppressWarnings("unchecked")
    private Class<T> calculateParameterType(Iterable<Matcher<? super T>> matchers) {
        Class<? super T> parameterType = null;
        for (Matcher<? super T> matcher : matchers) {
            Class<? super T> matcherType = matcher.getParameterType();
            if (null != matcherType && (null == parameterType || parameterType.isAssignableFrom(matcherType))) {
                parameterType = matcherType;
            }
        }
        return (Class<T>)parameterType;
    }

    @Override
    public boolean matches(Object o, Description mismatchDescription) {
        Set<String> mismatchDescriptions = new LinkedHashSet<String>();
        boolean result = !shortcut;
        for (Matcher<? super T> matcher : matchers) {
            boolean matches = matcher.matches(o);
            StringDescription stringDescription=new StringDescription();
            matcher.describeMismatch(o, stringDescription);
            mismatchDescriptions.add(stringDescription.toString());
            if (shortcut == matches) {
                result = shortcut;
                break;
            }
        }
        boolean first = true;
        for (String description : mismatchDescriptions) {
            if (first) { first = false; } else { mismatchDescription.appendText(" and "); }
            mismatchDescription.appendText(description);
        }
        return result;
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("(", " " + operator + " ", ")", matchers);
    }

    @Override
    public Class<T> getParameterType() {
        return parameterType;
    }
}
