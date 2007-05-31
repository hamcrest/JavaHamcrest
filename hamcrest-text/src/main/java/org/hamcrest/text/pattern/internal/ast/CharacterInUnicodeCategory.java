package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class CharacterInUnicodeCategory implements PatternComponent {
    private final String categoryName;

    public CharacterInUnicodeCategory(String categoryName) {
	this.categoryName = categoryName;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	builder.append("\\p{Is").append(categoryName).append("}");
    }
}
