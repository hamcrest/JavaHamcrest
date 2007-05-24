package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class GroupReference implements PatternComponent {
    private final String name;

    public GroupReference(String name) {
	this.name = name;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	builder.append("\\").append(groups.resolve(name));
    }
}
