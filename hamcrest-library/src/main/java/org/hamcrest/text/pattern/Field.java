package org.hamcrest.text.pattern;

import org.hamcrest.text.pattern.internal.ast.CaptureGroup;
import org.hamcrest.text.pattern.internal.ast.Literal;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class Field implements PatternComponent {
    private String name;
    private PatternComponent sequence;

    public Field(String name, PatternComponent pattern) {
	this.name = name;
	// TODO: clean this up and make the delimiter changeable
	Object[] args = new Object[] { "\"", new CaptureGroup(name, pattern),
		new Literal("\"") };
	this.sequence = Patterns.sequence(args);
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	sequence.buildRegex(builder, groups);
    }

    public String getName() {
	return name;
    }

}
