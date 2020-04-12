package org.hamcrest.reflection;


import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.reflection.VisibilityMatchers.isProtected;
import static org.hamcrest.reflection.VisibilityMatchers.isPublic;

@SuppressWarnings("unused")
public class IsProtectedTest extends AbstractMatcherTest {
    @Override
    protected Matcher<?> createMatcher() {
        return isProtected();
    }

    @Test
    public void test_packageExposesPublicFactoryMethod() throws NoSuchMethodException {
        assertMatches(isPublic(), VisibilityMatchers.class.getMethod("isProtected"));
    }

    @Test
    public void test_isProtected_matchesOnlyProtectedClasses() {
        assertDoesNotMatch(isProtected(), PublicClass.class);
        assertMatches(isProtected(), ProtectedClass.class);
        assertDoesNotMatch(isProtected(), PackageProtectedClass.class);
        assertDoesNotMatch(isProtected(), PrivateClass.class);

        assertDescription("is protected", isProtected());

        assertMismatchDescription("was a public class", isProtected(), PublicClass.class);
        assertMismatchDescription("was a package-protected (no modifiers) class", isProtected(), PackageProtectedClass.class);
        assertMismatchDescription("was a private class", isProtected(), PrivateClass.class);
    }


    @Test
    public void test_isProtected_matchesOnlyProtectedFields() throws NoSuchFieldException {
        Field publicField = ExampleFields.class.getDeclaredField("publicField");
        Field protectedField = ExampleFields.class.getDeclaredField("protectedField");
        Field packageProtectedField = ExampleFields.class.getDeclaredField("packageProtectedField");
        Field privateField = ExampleFields.class.getDeclaredField("privateField");

        assertDoesNotMatch(isProtected(), publicField);
        assertMatches(isProtected(), protectedField);
        assertDoesNotMatch(isProtected(), packageProtectedField);
        assertDoesNotMatch(isProtected(), privateField);

        assertDescription("is protected", isProtected());

        assertMismatchDescription("was a public java.lang.reflect.Field", isProtected(), publicField);
        assertMismatchDescription("was a package-protected (no modifiers) java.lang.reflect.Field", isProtected(), packageProtectedField);
        assertMismatchDescription("was a private java.lang.reflect.Field", isProtected(), privateField);
    }


    @Test
    public void test_isProtected_matchesOnlyProtectedMethods() throws NoSuchMethodException {
        Method publicMethod = ExampleMethods.class.getDeclaredMethod("publicMethod");
        Method protectedMethod = ExampleMethods.class.getDeclaredMethod("protectedMethod");
        Method packageProtectedMethod = ExampleMethods.class.getDeclaredMethod("packageProtectedMethod");
        Method privateMethod = ExampleMethods.class.getDeclaredMethod("privateMethod");

        assertDoesNotMatch(isProtected(), publicMethod);
        assertMatches(isProtected(), protectedMethod);
        assertDoesNotMatch(isProtected(), packageProtectedMethod);
        assertDoesNotMatch(isProtected(), privateMethod);

        assertDescription("is protected", isProtected());

        assertMismatchDescription("was a public java.lang.reflect.Method", isProtected(), publicMethod);
        assertMismatchDescription("was a package-protected (no modifiers) java.lang.reflect.Method", isProtected(), packageProtectedMethod);
        assertMismatchDescription("was a private java.lang.reflect.Method", isProtected(), privateMethod);
    }

    @Test
    public void test_isProtected_doesNotMatchNull() {
        assertDoesNotMatch(isProtected(), null);

        assertMismatchDescription("was null", isProtected(), null);
    }

    @Test
    public void test_isProtected_doesNotMatchNonReflectiveElement() {
        assertDoesNotMatch(isProtected(), new Object());

        assertMismatchDescription("was java.lang.Object instead of a reflective element like a Class<T>, Constructor<T>, or Method", isProtected(), new Object());
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