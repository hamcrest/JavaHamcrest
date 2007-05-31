package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;

public class ZeroOrMore extends PatternModifier {
    public ZeroOrMore(PatternComponent pattern) {
	super(pattern);
    }

    protected void appendModifier(StringBuilder builder) {
	builder.append("*");
    }
}
