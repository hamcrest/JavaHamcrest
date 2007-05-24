package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class CharacterNotInUnicodeCategory implements PatternComponent {
    private final String categoryName;

    public CharacterNotInUnicodeCategory(String categoryName) {
	this.categoryName = categoryName;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	builder.append("\\P{Is").append(categoryName).append("}");
    }
}
