package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;

public class OneOrMore extends PatternModifier implements PatternComponent {
    public OneOrMore(PatternComponent pattern) {
	super(pattern);
    }

    protected void appendModifier(StringBuilder builder) {
	builder.append("+");
    }
}
