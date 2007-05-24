package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class NullPatternComponent implements PatternComponent {
    public static final PatternComponent INSTANCE = new NullPatternComponent();

    private NullPatternComponent() {
	// Private so you have to use the only INSTANCE
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
	// Do nothing
    }
}
