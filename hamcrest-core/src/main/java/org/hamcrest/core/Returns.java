package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Aliases for is() to allow for more expressive tests of
 * method return values.
 * 
 * For example: assertThat(object.calculateValue(), returns(5))
 *
 */
public class Returns {

	/**
	 * An alias to <code>is(Matcher&lt;T&gt; matcher)</code> to allow for more readable
	 * expressions and concise code when testing method return values.
	 * <p/>
	 * For example:
	 * <pre>assertThat(object.calculateValue(), returns(greaterThan(5)));</pre>
	 * instead of:
	 * <pre>int actualValue = object.calculateValue();</pre>
	 * <pre>assertThat(actualValue, is(greaterThan(5)));</pre>
	 */
	@Factory
    public static <T> Matcher<T> returns(Matcher<T> matcher) {
        return Is.is(matcher);
    }
	
	/**
	 * An alias to <code>is(T operand)</code> to allow for more readable
	 * expressions and concise code when testing method return values.
	 * <p/>
	 * For example:
	 * <pre>assertThat(object.getValue(), returns(5));</pre>
	 * instead of:
	 * <pre>int actualValue = object.getValue();</pre>
	 * <pre>assertThat(actualValue, is(5));</pre>
	 */
	@Factory
    public static <T> Matcher<T> returns(T operand) {
        return Is.is(operand);
    }
}
