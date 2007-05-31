package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public abstract class PatternModifier implements PatternComponent {
    private PatternComponent pattern;

    public PatternModifier(PatternComponent pattern) {
	this.pattern = pattern;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	builder.append("(?:");
	pattern.buildRegex(builder, groups);
	builder.append(")");
	appendModifier(builder);
    }

    protected abstract void appendModifier(StringBuilder builder);
}
