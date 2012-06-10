/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * Provides a custom description to another matcher.
 */
public class DescribedAs<T> extends BaseMatcher<T> {
    private final String descriptionTemplate;
    private final Matcher<T> matcher;
    private final Object[] values;
    
    private final static Pattern ARG_PATTERN = Pattern.compile("%([0-9]+)"); 
    
    public DescribedAs(String descriptionTemplate, Matcher<T> matcher, Object[] values) {
        this.descriptionTemplate = descriptionTemplate;
        this.matcher = matcher;
        this.values = values.clone();
    }
    
    @Override
    public boolean matches(Object o) {
        return matcher.matches(o);
    }

    @Override
    public void describeTo(Description description) {
        java.util.regex.Matcher arg = ARG_PATTERN.matcher(descriptionTemplate);
        
        int textStart = 0;
        while (arg.find()) {
            description.appendText(descriptionTemplate.substring(textStart, arg.start()));
            description.appendValue(values[parseInt(arg.group(1))]);
            textStart = arg.end();
        }
        
        if (textStart < descriptionTemplate.length()) {
            description.appendText(descriptionTemplate.substring(textStart));
        }
    }
    
    @Override
    public void describeMismatch(Object item, Description description) {
        matcher.describeMismatch(item, description);
    }

    /**
     * Wraps an existing matcher, overriding its description with that specified.  All other functions are
     * delegated to the decorated matcher, including its mismatch description.
     * <p/>
     * For example:
     * <pre>describedAs("a big decimal equal to %0", equalTo(myBigDecimal), myBigDecimal.toPlainString())</pre> 
     * 
     * @param description
     *     the new description for the wrapped matcher
     * @param matcher
     *     the matcher to wrap
     * @param values
     *     optional values to insert into the tokenised description
     */
    @Factory
    public static <T> Matcher<T> describedAs(String description, Matcher<T> matcher, Object... values) {
        return new DescribedAs<T>(description, matcher, values);
    }
}
