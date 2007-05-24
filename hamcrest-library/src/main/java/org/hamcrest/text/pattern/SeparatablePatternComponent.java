package org.hamcrest.text.pattern;


public interface SeparatablePatternComponent extends PatternComponent {
	PatternComponent separatedBy(Object separator);
}
