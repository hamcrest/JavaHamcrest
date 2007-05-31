package org.hamcrest.text.pattern;

import org.hamcrest.text.pattern.internal.ast.AnyCharacter;
import org.hamcrest.text.pattern.internal.ast.CaptureGroup;
import org.hamcrest.text.pattern.internal.ast.CharacterInRange;
import org.hamcrest.text.pattern.internal.ast.CharacterInUnicodeCategory;
import org.hamcrest.text.pattern.internal.ast.CharacterNotInRange;
import org.hamcrest.text.pattern.internal.ast.CharacterNotInUnicodeCategory;
import org.hamcrest.text.pattern.internal.ast.Choice;
import org.hamcrest.text.pattern.internal.ast.Exactly;
import org.hamcrest.text.pattern.internal.ast.GroupReference;
import org.hamcrest.text.pattern.internal.ast.ListOf;
import org.hamcrest.text.pattern.internal.ast.Literal;
import org.hamcrest.text.pattern.internal.ast.OneOrMore;
import org.hamcrest.text.pattern.internal.ast.Optional;
import org.hamcrest.text.pattern.internal.ast.Sequence;
import org.hamcrest.text.pattern.internal.ast.ZeroOrMore;

public abstract class Patterns {
    public static PatternComponent text(String text) {
	return new Literal(text);
    }

    public static PatternComponent anyCharacter() {
	return AnyCharacter.INSTANCE;
    }

    public static PatternComponent anyCharacterIn(String range) {
	return new CharacterInRange(range);
    }

    public static PatternComponent anyCharacterNotIn(String range) {
	return new CharacterNotInRange(range);
    }

    public static PatternComponent anyCharacterInCategory(String category) {
	return new CharacterInUnicodeCategory(category);
    }

    public static PatternComponent anyCharacterNotInCategory(String category) {
	return new CharacterNotInUnicodeCategory(category);
    }

    public static PatternComponent either(Object... alternatives) {
	return new Choice(toPatterns(alternatives));
    }

    public static PatternComponent sequence(Object... elements) {
	return new Sequence(toPatterns(elements));
    }

    public static PatternComponent optional(Object o) {
	return new Optional(toPattern(o));
    }

    public static PatternComponent zeroOrMore(Object o) {
	return new ZeroOrMore(toPattern(o));
    }

    public static PatternComponent oneOrMore(Object o) {
	return new OneOrMore(toPattern(o));
    }

    public static PatternComponent group(String name, PatternComponent pattern) {
	return new CaptureGroup(name, pattern);
    }

    public static PatternComponent valueOf(String name) {
	return new GroupReference(name);
    }

    public static PatternComponent exactly(int requiredCount, Object o) {
	return new Exactly(requiredCount, toPattern(o));
    }

    public static PatternComponent from(int minimumCount, int maximumCount,
	    Object o) {
	return new FromTo(minimumCount, maximumCount, toPattern(o));
    }

    public static SeparablePatternComponent listOf(Object element) {
	return new ListOf(toPattern(element), toPattern(","));
    }

    public static PatternComponent[] toPatterns(Object... alternatives) {
	PatternComponent[] patterns = new PatternComponent[alternatives.length];
	for (int i = 0; i < patterns.length; i++)
	    patterns[i] = toPattern(alternatives[i]);
	return patterns;
    }

    public static PatternComponent toPattern(Object object) {
	if (object instanceof PatternComponent) {
	    return (PatternComponent) object;
	} else {
	    return text(object.toString());
	}
    }
}
