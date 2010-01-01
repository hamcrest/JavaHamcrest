/*  Copyright (c) 2000-2010 hamcrest.org
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
      assertMismatchDescription("\"some text\" is a java.lang.String", instanceOf(Number.class), "some text");
    }

    public void testMatchesPrimitiveTypes() {
      assertThat(true, any(boolean.class));
      assertThat((byte)1, any(byte.class));
      assertThat('x', any(char.class));
      assertThat(5.0, any(double.class));
      assertThat(5.0f, any(float.class));
      assertThat(2, any(int.class));
      assertThat(4L, any(long.class));
      assertThat((short)1, any(short.class));
    }
    
    public void testInstanceOfRequiresACastToReturnTheCorrectTypeForUseInJMock() {
      @SuppressWarnings("unused")
      Integer anInteger = (Integer)with(instanceOf(Integer.class));
    }
    
    public void testAnyWillReturnTheCorrectTypeForUseInJMock() {
      @SuppressWarnings("unused")
      Integer anInteger = with(any(Integer.class));
    }
    
    
    private static <T> T with(@SuppressWarnings("unused") Matcher<T> matcher) {
      return null;
    }
    
}

