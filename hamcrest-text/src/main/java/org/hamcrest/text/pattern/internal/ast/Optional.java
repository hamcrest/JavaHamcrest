package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;

public class Optional extends PatternModifier implements PatternComponent {

    public Optional(PatternComponent pattern) {
        super(pattern);
    }

    @Override
    protected void appendModifier(StringBuilder builder) {
        builder.append("?");
    }
}
