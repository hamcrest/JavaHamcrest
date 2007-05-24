package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class Sequence implements PatternComponent {
    private final PatternComponent[] alternatives;

    public Sequence(PatternComponent[] alternatives) {
	this.alternatives = alternatives.clone();
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	for (PatternComponent alternative : alternatives) {
	    alternative.buildRegex(builder, groups);
	}
    }
}
