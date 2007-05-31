package org.hamcrest.text.pattern;

import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class FromTo implements PatternComponent {
    private int minimumNumber;
    private int maximumNumber;
    private PatternComponent repeatedPattern;

    public FromTo(int minimumNumber, int maximumNumber,
	    PatternComponent repeatedPattern) {
	this.minimumNumber = minimumNumber;
	this.maximumNumber = maximumNumber;
	this.repeatedPattern = repeatedPattern;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	repeatedPattern.buildRegex(builder, groups);
	builder.append("{");
	builder.append(minimumNumber);
	builder.append(",");
	builder.append(maximumNumber);
	builder.append("}");

    }
}
