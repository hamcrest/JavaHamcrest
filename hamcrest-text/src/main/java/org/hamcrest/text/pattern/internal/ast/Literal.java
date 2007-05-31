package org.hamcrest.text.pattern.internal.ast;

import java.util.regex.Pattern;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class Literal implements PatternComponent {
    private String literal;

    public Literal(String literal) {
	this.literal = literal;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	builder.append(Pattern.quote(literal));
    }
}
