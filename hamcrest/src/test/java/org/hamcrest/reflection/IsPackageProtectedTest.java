package org.hamcrest.reflection;


import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.reflection.VisibilityMatchers.isPackageProtected;
import static org.hamcrest.reflection.VisibilityMatchers.isPublic;

@SuppressWarnings("unused")
public class IsPackageProtectedTest extends AbstractMatcherTest {
    @Override
    protected Matcher<?> createMatcher() {
        return isPackageProtected();
    }

    @Test
    public void test_packageExposesPublicFactoryMethod() throws NoSuchMethodException {
        assertMatches(isPublic(), VisibilityMatchers.class.getMethod("isPackageProtected"));
    }

    @Test
    public void test_isPackageProtected_matchesOnlyPackageProtectedClasses() {
        assertDoesNotMatch(isPackageProtected(), PublicClass.class);
        assertDoesNotMatch(isPackageProtected(), ProtectedClass.class);
        assertMatches(isPackageProtected(), PackageProtectedClass.class);
        assertDoesNotMatch(isPackageProtected(), PrivateClass.class);

        assertDescription("is package-protected (no modifiers)", isPackageProtected());

        assertMismatchDescription("was a public class", isPackageProtected(), PublicClass.class);
        assertMismatchDescription("was a protected class", isPackageProtected(), ProtectedClass.class);
        assertMismatchDescription("was a private class", isPackageProtected(), PrivateClass.class);
    }


    @Test
    public void test_isPackageProtected_matchesOnlyPackageProtectedFields() throws NoSuchFieldException {
        Field publicField = ExampleFields.class.getDeclaredField("publicField");
        Field protectedField = ExampleFields.class.getDeclaredField("protectedField");
        Field packageProtectedField = ExampleFields.class.getDeclaredField("packageProtectedField");
        Field privateField = ExampleFields.class.getDeclaredField("privateField");

        assertDoesNotMatch(isPackageProtected(), publicField);
        assertDoesNotMatch(isPackageProtected(), protectedField);
        assertMatches(isPackageProtected(), packageProtectedField);
        assertDoesNotMatch(isPackageProtected(), privateField);

        assertDescription("is package-protected (no modifiers)", isPackageProtected());

        assertMismatchDescription("was a public java.lang.reflect.Field", isPackageProtected(), publicField);
        assertMismatchDescription("was a protected java.lang.reflect.Field", isPackageProtected(), protectedField);
        assertMismatchDescription("was a private java.lang.reflect.Field", isPackageProtected(), privateField);
    }


    @Test
    public void test_isPackageProtected_matchesOnlyPackageProtectedMethods() throws NoSuchMethodException {
        Method publicMethod = ExampleMethods.class.getDeclaredMethod("publicMethod");
        Method protectedMethod = ExampleMethods.class.getDeclaredMethod("protectedMethod");
        Method packageProtectedMethod = ExampleMethods.class.getDeclaredMethod("packageProtectedMethod");
        Method privateMethod = ExampleMethods.class.getDeclaredMethod("privateMethod");

        assertDoesNotMatch(isPackageProtected(), publicMethod);
        assertDoesNotMatch(isPackageProtected(), protectedMethod);
        assertMatches(isPackageProtected(), packageProtectedMethod);
        assertDoesNotMatch(isPackageProtected(), privateMethod);

        assertDescription("is package-protected (no modifiers)", isPackageProtected());

        assertMismatchDescription("was a public java.lang.reflect.Method", isPackageProtected(), publicMethod);
        assertMismatchDescription("was a protected java.lang.reflect.Method", isPackageProtected(), protectedMethod);
        assertMismatchDescription("was a private java.lang.reflect.Method", isPackageProtected(), privateMethod);
    }

    @Test
    public void test_isPackageProtected_doesNotMatchNull() {
        assertDoesNotMatch(isPackageProtected(), null);

        assertMismatchDescription("was null", isPackageProtected(), null);
    }

    @Test
    public void test_isPackageProtected_doesNotMatchNonReflectiveElement() {
        assertDoesNotMatch(isPackageProtected(), new Object());

        assertMismatchDescription("was java.lang.Object instead of a reflective element like a Class<T>, Constructor<T>, or Method", isPackageProtected(), new Object());
    }

    public static class PublicClass {
    }

    protected static class ProtectedClass {
    }

    static class PackageProtectedClass {
    }

    private static class PrivateClass {
    }

    private static class ExampleFields {
        public Void publicField;
        protected Void protectedField;
        Void packageProtectedField;
        private Void privateField;
    }

    private static class ExampleMethods {
        public void publicMethod() {
        }

        protected void protectedMethod() {
        }

        void packageProtectedMethod() {
        }

        private void privateMethod() {
        }
    }
}