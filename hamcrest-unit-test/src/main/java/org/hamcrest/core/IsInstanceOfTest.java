/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.hamcrest.core.IsNot.not;

public class IsInstanceOfTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return instanceOf(Number.class);
    }

    public void testEvaluatesToTrueIfArgumentIsInstanceOfASpecificClass() {
        assertThat(1, instanceOf(Number.class));
        assertThat(1.0, instanceOf(Number.class));
        assertThat(null, not(instanceOf(Number.class)));
        assertThat(new Object(), not(instanceOf(Number.class)));
    }

    public void testHasAReadableDescription() {
        assertDescription("an instance of java.lang.Number", instanceOf(Number.class));
    }

    public void testDecribesActualClassInMismatchMessage() {
      assertMismatchDescription("[String] \"some text\"", instanceOf(Number.class), "some text");
    }
    
    public void testInstanceOfRequiresACastToReturnTheCorrectTypeForUseInJMock() {
      @SuppressWarnings("unused")
      Integer anInteger = (Integer)with(instanceOf(Integer.class));
    }

    public void testAnyWillReturnTheCorrectTypeForUseInJMock() {
      @SuppressWarnings("unused")
      Integer anInteger = with(any(Integer.class));
    }
    
    
    private static <T> T with(Matcher<T> matcher) {
      return null;
    }
    
}

