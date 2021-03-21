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
package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;


/**
 * Convenient base class for Matchers that require a non-null value of a specific type
 * and that will report why the received value has been rejected.
 * This implements the null check, checks the type and then casts. 
 * To use, implement <pre>matchesSafely()</pre>. 
 *
 * @param <T>
 * @author Neil Dunn
 * @author Nat Pryce
 * @author Steve Freeman
 */
public abstract class TypeSafeDiagnosingMatcher<T> extends BaseMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 2, 0); 
    private final Class<?> expectedType;

    /**
     * Subclasses should implement this. The item will already have been checked
     * for the specific type and will never be null.
     */
    protected abstract boolean matchesSafely(T item, Description mismatchDescription);

    /**
     * Use this constructor if the subclass that implements <code>matchesSafely</code> 
     * is <em>not</em> the class that binds &lt;T&gt; to a type. 
     * @param expectedType The expectedType of the actual value.
     */
    protected TypeSafeDiagnosingMatcher(Class<?> expectedType) {
      this.expectedType = expectedType;
    }

    /**
     * Use this constructor if the subclass that implements <code>matchesSafely</code> 
     * is <em>not</em> the class that binds &lt;T&gt; to a type. 
     * @param typeFinder A type finder to extract the type
     */
    protected TypeSafeDiagnosingMatcher(ReflectiveTypeFinder typeFinder) {
      this.expectedType = typeFinder.findExpectedType(getClass()); 
    }

    /**
     * The default constructor for simple sub types
     */
    protected TypeSafeDiagnosingMatcher() {
      this(TYPE_FINDER); 
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean matches(Object item) {
        return item != null
            && expectedType.isInstance(item)
            && matchesSafely((T) item, new Description.NullDescription());
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void describeMismatch(Object item, Description mismatchDescription) {
      if (item == null) {
        mismatchDescription.appendText("was null");
    } else if (!expectedType.isInstance(item)) {
      mismatchDescription.appendText("was ")
            .appendText(item.getClass().getSimpleName())
            .appendText(" ")
            .appendValue(item);
    } else {
        matchesSafely((T) item, mismatchDescription);
      }
    }
}
