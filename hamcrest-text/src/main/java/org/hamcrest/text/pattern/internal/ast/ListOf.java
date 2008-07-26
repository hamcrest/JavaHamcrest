package org.hamcrest.text.pattern.internal.ast;

import static org.hamcrest.text.pattern.Patterns.optional;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.hamcrest.text.pattern.Patterns.toPattern;
import static org.hamcrest.text.pattern.Patterns.zeroOrMore;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.SeparablePatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class ListOf implements SeparablePatternComponent {
    private final PatternComponent element;
    private final PatternComponent separator;

    public ListOf(PatternComponent element, PatternComponent separator) {
        this.element = element;
        this.separator = separator;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
        optional(sequence(element, zeroOrMore(sequence(separator, element))))
            .buildRegex(builder, groups);
    }

    public PatternComponent separatedBy(Object separator) {
        return new ListOf(element, toPattern(separator));
    }
}
