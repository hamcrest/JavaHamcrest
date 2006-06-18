/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.isA;
import static org.hamcrest.core.IsNot.not;

public class IsInstanceOfTest extends AbstractMatcherTest {
    public void testEvaluatesToTrueIfArgumentIsInstanceOfASpecificClass() {
        assertThat(1, isA(Number.class));
        assertThat(1.0, isA(Number.class));
//        assertThat("string", not(isA(Number.class)));
        assertThat(null, not(isA(Number.class)));
    }
}
