package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;

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
        assertMatches("should match", new IsCompatibleType(BaseClass.class), BaseClass.class);
    }

    public void testMatchesSameInterface() {
        assertMatches("should match", new IsCompatibleType(BaseInterface.class), BaseInterface.class);
    }

    public void testMatchesExtendedClass() {
        assertMatches("should match", new IsCompatibleType(BaseClass.class), ExtendedClass.class);
    }

    public void testMatchesClassImplementingInterface() {
        assertMatches("should match", new IsCompatibleType(BaseInterface.class), ClassImplementingBaseInterface.class);
    }

    public void testMatchesExtendedInterface() {
        assertMatches("should match", new IsCompatibleType(BaseInterface.class), ExtendedInterface.class);
    }

    public void testDoesNotMatchIncompatibleTypes() {
        assertDoesNotMatch("should not match base type", new IsCompatibleType(ExtendedClass.class), BaseClass.class);
        assertDoesNotMatch("should not match incompatible types", new IsCompatibleType(String.class), Integer.class);
    }

    public void testDoesNotMatchObjectsThatAreNotClasses() {
        assertDoesNotMatch("should not match", new IsCompatibleType(BaseClass.class), "a string");
    }

    public void testHasReadableDescription() {
        assertDescription("type < java.lang.Runnable",
                new IsCompatibleType(Runnable.class));
    }
}
