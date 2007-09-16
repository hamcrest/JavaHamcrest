package org.hamcrest.text.pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.pattern.PatternMatcher.matchesPattern;
import static org.hamcrest.text.pattern.Patterns.anyCharacter;
import static org.hamcrest.text.pattern.Patterns.anyCharacterIn;
import static org.hamcrest.text.pattern.Patterns.anyCharacterInCategory;
import static org.hamcrest.text.pattern.Patterns.anyCharacterNotIn;
import static org.hamcrest.text.pattern.Patterns.anyCharacterNotInCategory;
import static org.hamcrest.text.pattern.Patterns.either;
import static org.hamcrest.text.pattern.Patterns.exactly;
import static org.hamcrest.text.pattern.Patterns.from;
import static org.hamcrest.text.pattern.Patterns.group;
import static org.hamcrest.text.pattern.Patterns.listOf;
import static org.hamcrest.text.pattern.Patterns.oneOrMore;
import static org.hamcrest.text.pattern.Patterns.optional;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.hamcrest.text.pattern.Patterns.text;
import static org.hamcrest.text.pattern.Patterns.valueOf;
import static org.hamcrest.text.pattern.Patterns.zeroOrMore;
import junit.framework.TestCase;

public class PatternMatcherTests extends TestCase {
	public void testMatchesPlainText() {
		PatternMatcher matcher = new PatternMatcher(
			text("text"));
		
		assertThat("text", matchesPattern(matcher));
		assertThat("xxxtextxxx", not(matchesPattern(matcher)));
		assertThat("tex", not(matchesPattern(matcher)));
		assertThat("blah", not(matchesPattern(matcher)));
	}

	public void testMatchesPlainTextContainingSpecialRegexCharacters() {
		PatternMatcher matcher = new PatternMatcher(
			text("*star*"));
		
		assertThat("*star*", matchesPattern(matcher));
	}
	
	public void testMatchesSequenceOfText() {
		PatternMatcher matcher = new PatternMatcher(
			sequence(
				"hello",
				" ",
				"world"));
		
		assertThat("hello world", matchesPattern(matcher));
	}
	
	public void testMatchesAlternatives() {
		PatternMatcher matcher = new PatternMatcher(
			either(
				text("hello"), 
				text("world")));
		
		assertThat("hello", matchesPattern(matcher));
		assertThat("world", matchesPattern(matcher));
		assertThat("hello world", not(matchesPattern(matcher)));
	}
	
	public void testMatchesOptionalPattern() {
		PatternMatcher matcher = new PatternMatcher(
			sequence(text("hello"),
					 optional(text(" world"))));	
		
		assertThat("hello", matchesPattern(matcher));
		assertThat("hello world", matchesPattern(matcher));
		assertThat(" world", not(matchesPattern(matcher)));
	}

	public void testMatchesRepetitionZeroOrMoreTimes() {
		PatternMatcher matcher = new PatternMatcher(
			zeroOrMore(text("x")));
		
		assertThat("", matchesPattern(matcher));
		assertThat("x", matchesPattern(matcher));
		assertThat("xxx", matchesPattern(matcher));
		assertThat(" xx", not(matchesPattern(matcher)));
		assertThat("x x", not(matchesPattern(matcher)));
		assertThat("xx ", not(matchesPattern(matcher)));
	}
	
	public void testMatchesRepetitionOneOrMoreTimes() {
		PatternMatcher matcher = new PatternMatcher(
			oneOrMore(text("x")));
		
		assertThat("", not(matchesPattern(matcher)));
		assertThat("x", matchesPattern(matcher));
		assertThat("xxx", matchesPattern(matcher));
		assertThat(" xx", not(matchesPattern(matcher)));
		assertThat("x x", not(matchesPattern(matcher)));
		assertThat("xx ", not(matchesPattern(matcher)));
	}
	
	public void testCanMatchAnyCharacter() {
		PatternMatcher matcher = new PatternMatcher(
			sequence(text("x"), anyCharacter(), text("y")));
		
		assertThat("x.y", matchesPattern(matcher));
		assertThat("xzy", matchesPattern(matcher));
		assertThat("xy", not(matchesPattern(matcher)));
	}
	
	public void testCapturesMatchedGroups() throws Exception {
		PatternMatcher matcher = new PatternMatcher(
			sequence(
				group("xs", oneOrMore(text("x"))),
				group("ys", oneOrMore(text("y")))));
		
		Parse parse;
		
		parse = matcher.parse("xxxyyy");
		assertEquals("xxx", parse.get("xs"));
		assertEquals("yyy", parse.get("ys"));
		
		parse = matcher.parse("xxyyyyyy");
		assertEquals("xx", parse.get("xs"));
		assertEquals("yyyyyy", parse.get("ys"));
	}
	
	public void testFailsIfDoesNotMatchParseInput() {
		PatternMatcher matcher = new PatternMatcher(
			text("expected input"));
		
		try {
			matcher.parse("input that doesn't match");
			fail("should have thrown ParseException");
		}
		catch (PatternMatchException ex) {
			// expected
		}
	}
	
	public void testCanReferToContentOfMatchedGroups() throws Exception {
		PatternMatcher matcher = new PatternMatcher(
			sequence(
				group("first", oneOrMore(text("x"))),
				text("-"),
				valueOf("first")));
		
		assertThat("x-x", matchesPattern(matcher));
		assertThat("xx-xx", matchesPattern(matcher));
		
		assertThat("x-xx", not(matchesPattern(matcher)));
		assertThat("xx-x", not(matchesPattern(matcher)));
	}
	
	PatternMatcher scopedMatcher = new PatternMatcher(
		sequence(
			group("xs", oneOrMore(text("x"))),
			group("inside",
				sequence(
					group("xs", oneOrMore(text("X"))),
				    valueOf("xs"))),
			valueOf("xs")));

	public void testReferencesToGroupsAreLexicallyScoped() throws Exception {
		assertThat("xxXXXXxx", matchesPattern(scopedMatcher));
		assertThat("xXXx", matchesPattern(scopedMatcher));
		assertThat("xXxx", not(matchesPattern(scopedMatcher)));
		assertThat("xXXX", not(matchesPattern(scopedMatcher)));
	}
	
	public void testNamesInInnerScopesCanBeQueriedUsingDottedPathNotation() throws Exception {
		Parse parse = scopedMatcher.parse("xxXXXXXXxx");
		assertEquals("xx", parse.get("xs"));
		assertEquals("XXX", parse.get("inside.xs"));
	}
	
	public void testCanReferToValuesOfGroupsInInnerScopesUsingDottedPathNotation() {
		PatternMatcher matcher = new PatternMatcher(
			sequence(
				group("xs", oneOrMore(text("x"))),
				group("inside",
					sequence(
						group("xs", oneOrMore(text("X"))),
					    valueOf("xs"))),
				valueOf("xs"),
				valueOf("inside.xs")));
		
		assertThat("xXXXXxXX", matchesPattern(matcher));
		assertThat("xxXXxxX", matchesPattern(matcher));
	}
	
	public void testCanDefinePatternsInTermsOfExistingPatterns() {
		PatternMatcher emailAddressMatcher = new PatternMatcher(
			sequence(
				group("user", oneOrMore(anyCharacter())),
				"@",
				group("host", oneOrMore(anyCharacter()))));
		
		PatternMatcher mailToURLMatcher = new PatternMatcher(
			sequence(
				group("scheme", text("mailto")),
				":",
				group("email", emailAddressMatcher)));
		
		assertThat("mailto:npryce@users.sf.net", matchesPattern(mailToURLMatcher));
	}
	
	public void testMatchesCharacterInList() {
		PatternMatcher matcher = new PatternMatcher(
			anyCharacterIn("0123456789"));
		
		for (int i = 0; i < 9; i++) {
			String input = String.valueOf(i);
			
			assertThat(input, matchesPattern(matcher));
		}
		assertThat("X", not(matchesPattern(matcher)));
	}
	
	public void testMatchesCharacterRange() {
		PatternMatcher matcher = new PatternMatcher(
			anyCharacterIn("0-9"));
		
		for (int i = 0; i < 9; i++) {
			String input = String.valueOf(i);
			
			assertThat(input, matchesPattern(matcher));
		}
		assertThat("X", not(matchesPattern(matcher)));
	}

	public void testMatchesCharacterNotInRange() {
		PatternMatcher matcher = new PatternMatcher(
			anyCharacterNotIn("0-9"));
		
		for (int i = 0; i < 9; i++) {
			String input = String.valueOf(i);
			
			assertThat(input, not(matchesPattern(matcher)));
		}
		assertThat("X", matchesPattern(matcher));
	}

	public void testMatchesCharactersInUnicodeCategories() {
		PatternMatcher matcher = new PatternMatcher(
			anyCharacterInCategory("Lu"));
		
		assertThat("A", matchesPattern(matcher));
		assertThat("a", not(matchesPattern(matcher)));
		assertThat("B", matchesPattern(matcher));
		assertThat("b", not(matchesPattern(matcher)));
	}
	
	public void testMatchesCharactersNotInUnicodeCategories() {
		PatternMatcher matcher = new PatternMatcher(
			anyCharacterNotInCategory("Lu"));
		
		assertThat("A", not(matchesPattern(matcher)));
		assertThat("a", matchesPattern(matcher));
		assertThat("B", not(matchesPattern(matcher)));
		assertThat("b", matchesPattern(matcher));
	}
	
	public void testMatchesExactlyTheSpecifiedNumberOfRepetitions() {
		PatternMatcher matcher = new PatternMatcher(
			exactly(3, "x"));
		
		assertThat("xx", not(matchesPattern(matcher)));
		assertThat("xxx", matchesPattern(matcher));
		assertThat("xxxx", not(matchesPattern(matcher)));
	}
	
	public void testMatchesARangeOfAllowableRepetitions() {
		PatternMatcher matcher = new PatternMatcher(
				from(3, 5, "x"));
		
		assertThat("xx", not(matchesPattern(matcher)));
		assertThat("xxx", matchesPattern(matcher));
		assertThat("xxxx", matchesPattern(matcher));
		assertThat("xxxxx", matchesPattern(matcher));
		assertThat("xxxxxx", not(matchesPattern(matcher)));
	}
	
	public void testMatchesAListOfMatchedThings() {
		PatternMatcher matcher = new PatternMatcher(
			listOf("x"));
		
		assertThat("", matchesPattern(matcher));
		assertThat("x", matchesPattern(matcher));
		assertThat("x,x", matchesPattern(matcher));
		assertThat("x,x,x,x,x", matchesPattern(matcher));
		
		assertThat(",", not(matchesPattern(matcher)));
		assertThat("x,x,x,", not(matchesPattern(matcher)));
	}

	public void testMatchesAListWithSpecificSeparator() {
		PatternMatcher matcher = new PatternMatcher(
			listOf("x").separatedBy(":"));
		
		assertThat("", matchesPattern(matcher));
		assertThat("x", matchesPattern(matcher));
		assertThat("x:x", matchesPattern(matcher));
		assertThat("x:x:x:x:x", matchesPattern(matcher));
		
		assertThat("x,x,x", not(matchesPattern(matcher)));
	}
}

