/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.object.IsCompatibleType.typeCompatibleWith;

public class IsCompatibleTypeTest extends AbstractMatcherTest {
    static class BaseClass {
    }

    private static class ExtendedClass extends BaseClass {
    }

    interface BaseInterface {
    }

    interface ExtendedInterface extends BaseInterface {
    }

    private static class ClassImplementingBaseInterface implements BaseInterface {
    }

    @Override
    protected Matcher<?> createMatcher() {
        return typeCompatibleWith(BaseClass.class);
    }

    public void testMatchesSameClass() {
        assertThat(BaseClass.class, typeCompatibleWith(BaseClass.class));
    }

    public void testMatchesSameInterface() {
        assertThat(BaseInterface.class, typeCompatibleWith(BaseInterface.class));
    }

    public void testMatchesExtendedClass() {
        assertThat(ExtendedClass.class, typeCompatibleWith(BaseClass.class));
    }

    public void testMatchesClassImplementingInterface() {
        assertThat(ClassImplementingBaseInterface.class, typeCompatibleWith(BaseInterface.class));
    }

    public void testMatchesExtendedInterface() {
        assertThat(ExtendedInterface.class, typeCompatibleWith(BaseInterface.class));
    }

    public void testHasReadableDescription() {
        assertDescription("type < java.lang.Runnable", typeCompatibleWith(Runnable.class));
    }
}
