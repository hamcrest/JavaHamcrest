package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class Choice implements PatternComponent {
    private final PatternComponent[] alternatives;

    public Choice(PatternComponent[] alternatives) {
	this.alternatives = alternatives.clone();
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	builder.append("(?:");
	boolean needsSeparator = false;
	for (PatternComponent alternative : alternatives) {
	    if (needsSeparator) {
		builder.append("|");
	    } else {
		needsSeparator = true;
	    }

	    alternative.buildRegex(builder, groups);
	}
	builder.append(")");
    }
}
