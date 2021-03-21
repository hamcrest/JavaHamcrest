/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
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
    public static <T> Matcher<T> describedAs(String description, Matcher<T> matcher, Object... values) {
        return new DescribedAs<T>(description, matcher, values);
    }
}
