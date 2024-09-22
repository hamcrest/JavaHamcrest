package org.hamcrest.object;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.test.MatcherAssertions.assertDescription;
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

    @Test
    public void testMatchesSameClass() {
        assertThat(BaseClass.class, typeCompatibleWith(BaseClass.class));
    }

    @Test
    public void testMatchesSameInterface() {
        assertThat(BaseInterface.class, typeCompatibleWith(BaseInterface.class));
    }

    @Test
    public void testMatchesExtendedClass() {
        assertThat(ExtendedClass.class, typeCompatibleWith(BaseClass.class));
    }

    @Test
    public void testMatchesClassImplementingInterface() {
        assertThat(ClassImplementingBaseInterface.class, typeCompatibleWith(BaseInterface.class));
    }

    @Test
    public void testMatchesExtendedInterface() {
        assertThat(ExtendedInterface.class, typeCompatibleWith(BaseInterface.class));
    }

    @Test
    public void testHasReadableDescription() {
        assertDescription("type < java.lang.Runnable", typeCompatibleWith(Runnable.class));
    }

}
