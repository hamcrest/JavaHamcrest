package org.hamcrest.text.pattern;

import java.util.regex.MatchResult;

import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class Parse {
    private final GroupNamespace groups;
    private final MatchResult result;

    public Parse(GroupNamespace groups, MatchResult result) {
	this.groups = groups;
	this.result = result;
    }

    public String get(String name) {
	int groupIndex = groups.resolve(name);
	return result.group(groupIndex);
    }
}
