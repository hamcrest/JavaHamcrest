package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class CaptureGroup implements PatternComponent {
    private String name;
    private PatternComponent pattern;

    public CaptureGroup(String name, PatternComponent pattern) {
	this.name = name;
	this.pattern = pattern;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	GroupNamespace subgroups = groups.create(name);
	builder.append("(");
	pattern.buildRegex(builder, subgroups);
	builder.append(")");
    }
}
