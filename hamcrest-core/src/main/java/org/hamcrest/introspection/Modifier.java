package org.hamcrest.introspection;

import org.hamcrest.Matcher;

public interface Modifier {
	Matcher<?> modified();
}
