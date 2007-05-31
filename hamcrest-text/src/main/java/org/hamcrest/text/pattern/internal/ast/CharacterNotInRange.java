package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class CharacterNotInRange implements PatternComponent {
    private final String range;

    public CharacterNotInRange(String range) {
	this.range = range;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	builder.append("[^").append(range).append("]");
    }
}
