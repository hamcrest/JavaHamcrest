package org.hamcrest.reflection;


import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.reflection.VisibilityMatchers.isPrivate;
import static org.hamcrest.reflection.VisibilityMatchers.isPublic;

@SuppressWarnings("unused")
public class IsPrivateTest extends AbstractMatcherTest {
    @Override
    protected Matcher<?> createMatcher() {
        return isPrivate();
    }

    @Test
    public void test_packageExposesPublicFactoryMethod() throws NoSuchMethodException {
        assertMatches(isPublic(), VisibilityMatchers.class.getMethod("isPrivate"));
    }

    @Test
    public void test_isPrivate_matchesOnlyProtectedClasses() {
        assertDoesNotMatch(isPrivate(), PublicClass.class);
        assertDoesNotMatch(isPrivate(), ProtectedClass.class);
        assertDoesNotMatch(isPrivate(), PackageProtectedClass.class);
        assertMatches(isPrivate(), PrivateClass.class);

        assertDescription("is private", isPrivate());

        assertMismatchDescription("was a public class", isPrivate(), PublicClass.class);
        assertMismatchDescription("was a protected class", isPrivate(), ProtectedClass.class);
        assertMismatchDescription("was a package-protected (no modifiers) class", isPrivate(), PackageProtectedClass.class);
    }


    @Test
    public void test_isPrivate_matchesOnlyProtectedFields() throws NoSuchFieldException {
        Field publicField = ExampleFields.class.getDeclaredField("publicField");
        Field protectedField = ExampleFields.class.getDeclaredField("protectedField");
        Field packageProtectedField = ExampleFields.class.getDeclaredField("packageProtectedField");
        Field privateField = ExampleFields.class.getDeclaredField("privateField");

        assertDoesNotMatch(isPrivate(), publicField);
        assertDoesNotMatch(isPrivate(), protectedField);
        assertDoesNotMatch(isPrivate(), packageProtectedField);
        assertMatches(isPrivate(), privateField);

        assertDescription("is private", isPrivate());

        assertMismatchDescription("was a public java.lang.reflect.Field", isPrivate(), publicField);
        assertMismatchDescription("was a protected java.lang.reflect.Field", isPrivate(), protectedField);
        assertMismatchDescription("was a package-protected (no modifiers) java.lang.reflect.Field", isPrivate(), packageProtectedField);
    }


    @Test
    public void test_isPrivate_matchesOnlyProtectedMethods() throws NoSuchMethodException {
        Method publicMethod = ExampleMethods.class.getDeclaredMethod("publicMethod");
        Method protectedMethod = ExampleMethods.class.getDeclaredMethod("protectedMethod");
        Method packageProtectedMethod = ExampleMethods.class.getDeclaredMethod("packageProtectedMethod");
        Method privateMethod = ExampleMethods.class.getDeclaredMethod("privateMethod");

        assertDoesNotMatch(isPrivate(), publicMethod);
        assertDoesNotMatch(isPrivate(), protectedMethod);
        assertDoesNotMatch(isPrivate(), packageProtectedMethod);
        assertMatches(isPrivate(), privateMethod);

        assertDescription("is private", isPrivate());

        assertMismatchDescription("was a public java.lang.reflect.Method", isPrivate(), publicMethod);
        assertMismatchDescription("was a protected java.lang.reflect.Method", isPrivate(), protectedMethod);
        assertMismatchDescription("was a package-protected (no modifiers) java.lang.reflect.Method", isPrivate(), packageProtectedMethod);
    }

    @Test
    public void test_isPrivate_doesNotMatchNull() {
        assertDoesNotMatch(isPrivate(), null);

        assertMismatchDescription("was null", isPrivate(), null);
    }

    @Test
    public void test_isPrivate_doesNotMatchNonReflectiveElement() {
        assertDoesNotMatch(isPrivate(), new Object());

        assertMismatchDescription("was java.lang.Object instead of a reflective element like a Class<T>, Constructor<T>, or Method", isPrivate(), new Object());
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