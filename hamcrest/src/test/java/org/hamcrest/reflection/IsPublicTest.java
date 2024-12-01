package org.hamcrest.reflection;


import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.reflection.VisibilityMatchers.isPublic;

@SuppressWarnings("unused")
public class IsPublicTest extends AbstractMatcherTest {
    @Override
    protected Matcher<?> createMatcher() {
        return isPublic();
    }

    @Test
    public void test_packageExposesPublicFactoryMethod() throws NoSuchMethodException {
        assertMatches(isPublic(), VisibilityMatchers.class);
        assertMatches(isPublic(), VisibilityMatchers.class.getMethod("isPublic"));
    }

    @Test
    public void test_isPublic_matchesOnlyPublicClasses() {
        assertMatches(isPublic(), PublicClass.class);
        assertDoesNotMatch(isPublic(), ProtectedClass.class);
        assertDoesNotMatch(isPublic(), PackageProtectedClass.class);
        assertDoesNotMatch(isPublic(), PrivateClass.class);

        assertDescription("is public", isPublic());

        assertMismatchDescription("was a protected class", isPublic(), ProtectedClass.class);
        assertMismatchDescription("was a package-protected (no modifiers) class", isPublic(), PackageProtectedClass.class);
        assertMismatchDescription("was a private class", isPublic(), PrivateClass.class);
    }


    @Test
    public void test_isPublic_matchesOnlyPublicFields() throws NoSuchFieldException {
        Field publicField = ExampleFields.class.getDeclaredField("publicField");
        Field protectedField = ExampleFields.class.getDeclaredField("protectedField");
        Field packageProtectedField = ExampleFields.class.getDeclaredField("packageProtectedField");
        Field privateField = ExampleFields.class.getDeclaredField("privateField");

        assertMatches(isPublic(), publicField);
        assertDoesNotMatch(isPublic(), protectedField);
        assertDoesNotMatch(isPublic(), packageProtectedField);
        assertDoesNotMatch(isPublic(), privateField);

        assertDescription("is public", isPublic());

        assertMismatchDescription("was a protected java.lang.reflect.Field", isPublic(), protectedField);
        assertMismatchDescription("was a package-protected (no modifiers) java.lang.reflect.Field", isPublic(), packageProtectedField);
        assertMismatchDescription("was a private java.lang.reflect.Field", isPublic(), privateField);
    }


    @Test
    public void test_isPublic_matchesOnlyPublicMethods() throws NoSuchMethodException {
        Method publicMethod = ExampleMethods.class.getDeclaredMethod("publicMethod");
        Method protectedMethod = ExampleMethods.class.getDeclaredMethod("protectedMethod");
        Method packageProtectedMethod = ExampleMethods.class.getDeclaredMethod("packageProtectedMethod");
        Method privateMethod = ExampleMethods.class.getDeclaredMethod("privateMethod");

        assertMatches(isPublic(), publicMethod);
        assertDoesNotMatch(isPublic(), protectedMethod);
        assertDoesNotMatch(isPublic(), packageProtectedMethod);
        assertDoesNotMatch(isPublic(), privateMethod);

        assertDescription("is public", isPublic());

        assertMismatchDescription("was a protected java.lang.reflect.Method", isPublic(), protectedMethod);
        assertMismatchDescription("was a package-protected (no modifiers) java.lang.reflect.Method", isPublic(), packageProtectedMethod);
        assertMismatchDescription("was a private java.lang.reflect.Method", isPublic(), privateMethod);
    }

    @Test
    public void test_isPublic_doesNotMatchNull() {
        assertDoesNotMatch(isPublic(), null);

        assertMismatchDescription("was null", isPublic(), null);
    }

    @Test
    public void test_isPublic_doesNotMatchNonReflectiveElement() {
        assertDoesNotMatch(isPublic(), new Object());

        assertMismatchDescription("was java.lang.Object instead of a reflective element like a Class<T>, Constructor<T>, or Method", isPublic(), new Object());
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