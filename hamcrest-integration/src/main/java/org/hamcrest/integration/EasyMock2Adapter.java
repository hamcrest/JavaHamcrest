/**
 * BSD License
 *
 * Copyright (c) 2000-2015 www.hamcrest.org
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
package org.hamcrest.integration;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * An adapter allowing a Hamcrest {@link org.hamcrest.Matcher}
 * to act as an EasyMock {@link org.easymock.IArgumentMatcher}.
 *
 * @author Joe Walnes
 */
public class EasyMock2Adapter implements IArgumentMatcher {

    /**
     * Convenience factory method that will adapt a
     * Hamcrest {@link org.hamcrest.Matcher} to act as an
     * EasyMock {@link org.easymock.IArgumentMatcher} and
     * report it to EasyMock so it can be kept track of.
     * @param matcher the matcher
     * @return the wrapped matcher
     */
    public static IArgumentMatcher adapt(Matcher<?> matcher) {
        EasyMock2Adapter easyMock2Matcher = new EasyMock2Adapter(matcher);
        EasyMock.reportMatcher(easyMock2Matcher);
        return easyMock2Matcher;
    }

    private final Matcher<?> hamcrestMatcher;

    public EasyMock2Adapter(Matcher<?> matcher) {
        this.hamcrestMatcher = matcher;
    }

    @Override
    public boolean matches(Object argument) {
        return hamcrestMatcher.matches(argument);
    }

    @Override
    public void appendTo(StringBuffer buffer) {
        hamcrestMatcher.describeTo(new StringDescription(buffer));
    }
}
