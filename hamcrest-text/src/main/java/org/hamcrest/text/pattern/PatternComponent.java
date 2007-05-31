package org.hamcrest.text.pattern;

import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public interface PatternComponent {
    void buildRegex(StringBuilder builder, GroupNamespace groups);
}
