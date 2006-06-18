package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCompatibleType.compatibleType;

public class IsCompatibleTypeTest extends AbstractMatcherTest {
    public static class BaseClass {
    }

    public static class ExtendedClass extends BaseClass {
    }

    public interface BaseInterface {
    }

    public interface ExtendedInterface extends BaseInterface {
    }

    public static class ClassImplementingBaseInterface implements BaseInterface {
    }

    public void testMatchesSameClass() {
        assertThat(BaseClass.class, compatibleType(BaseClass.class));
    }

    public void testMatchesSameInterface() {
        assertThat(BaseInterface.class, compatibleType(BaseInterface.class));
    }

    public void testMatchesExtendedClass() {
        assertThat(ExtendedClass.class, compatibleType(BaseClass.class));
    }

    public void testMatchesClassImplementingInterface() {
        assertThat(ClassImplementingBaseInterface.class, compatibleType(BaseInterface.class));
    }

    public void testMatchesExtendedInterface() {
        assertThat(ExtendedInterface.class, compatibleType(BaseInterface.class));
    }

//    public void testDoesNotMatchIncompatibleTypes() {
//        assertThat(BaseClass.class, not(compatibleType(ExtendedClass.class)));
//        assertThat(Integer.class, not(compatibleType(String.class)));
//    }

    public void testHasReadableDescription() {
        assertDescription("type < java.lang.Runnable", compatibleType(Runnable.class));
    }
}
