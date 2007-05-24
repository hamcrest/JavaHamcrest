package org.hamcrest.text.pattern;

public interface SeparablePatternComponent extends PatternComponent {
    PatternComponent separatedBy(Object separator);
}
