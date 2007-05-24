package org.hamcrest.text.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class PatternMatcher extends TypeSafeMatcher<String> implements PatternComponent {
    private GroupNamespace groups = new GroupNamespace();
    private PatternComponent root;
    private Pattern pattern;

    public PatternMatcher(PatternComponent root) {
	this.root = root;
	this.pattern = compile(root, groups);
    }

    @Factory
    public static PatternMatcher matchesPattern(PatternComponent pattern) {
	return new PatternMatcher(pattern);
    }

    @Factory
    public static PatternMatcher matchesPattern(PatternMatcher pattern) {
	return pattern;
    }

    public String toString() {
	return pattern.toString();
    }

    public void describeTo(Description description) {
	description.appendText("a string matching ");
	description.appendValue(this.toString());
    }

    public boolean matchesSafely(String s) {
	return pattern.matcher(s).matches();
    }

    public Parse parse(String input) throws PatternMatchException {
	Matcher matcher = pattern.matcher(input);
	if (matcher.matches()) {
	    return new Parse(groups, matcher);
	} else {
	    throw new PatternMatchException("did not match input: " + input);
	}
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	root.buildRegex(builder, groups);
    }

    private static Pattern compile(PatternComponent root, GroupNamespace groups) {
	StringBuilder builder = new StringBuilder();
	root.buildRegex(builder, groups);
	return Pattern.compile(builder.toString());
    }
}
