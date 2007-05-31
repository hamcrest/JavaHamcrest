package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class Exactly implements PatternComponent {
    private int requiredNumber;

    private PatternComponent repeatedPattern;

    public Exactly(int requiredNumber, PatternComponent repeatedPattern) {
	this.requiredNumber = requiredNumber;
	this.repeatedPattern = repeatedPattern;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	repeatedPattern.buildRegex(builder, groups);
	builder.append("{");
	builder.append(requiredNumber);
	builder.append("}");

    }
}
