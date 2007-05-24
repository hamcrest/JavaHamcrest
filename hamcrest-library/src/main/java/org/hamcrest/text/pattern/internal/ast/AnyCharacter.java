package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class AnyCharacter implements PatternComponent {
    public static final AnyCharacter INSTANCE = new AnyCharacter();

    private AnyCharacter() {
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	builder.append(".");
    }
}
