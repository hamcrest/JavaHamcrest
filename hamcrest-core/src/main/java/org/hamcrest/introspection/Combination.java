package org.hamcrest.introspection;

import org.hamcrest.Matcher;

public interface Combination {	
	Iterable<? extends Matcher<?>> combined();	
}
