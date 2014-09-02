package org.hamcrest.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.Test;

@SuppressWarnings("unused")
public final class ReflectiveFactoryReaderTest {

    public static class SimpleSetOfMatchers {

        @Factory
        public static Matcher<String> firstMethod() {
            return null;
        }

        @Factory
        public static Matcher<String> secondMethod() {
            return null;
        }

    }

    @Test public void
    iteratesOverFactoryMethods() {
        final ReflectiveFactoryReader reader = new ReflectiveFactoryReader(SimpleSetOfMatchers.class);
        final List<FactoryMethod> methods = methodsReadBy(reader);

        assertEquals(2, methods.size());
        
        final String expectedClass = SimpleSetOfMatchers.class.getName().replace('$', '.');
        
        assertEquals("firstMethod", methods.get(0).getName());
        assertEquals(expectedClass, methods.get(0).getMatcherClass());

        assertEquals("secondMethod", methods.get(1).getName());
        assertEquals(expectedClass, methods.get(1).getMatcherClass());
    }

    public static class MatchersWithDodgySignatures {

        @Factory
        public Matcher<String> notStatic() {
            return null;
        }

        @Factory
        static Matcher<String> notPublic() {
            return null;
        }

        public static Matcher<String> noAnnotation() {
            return null;
        }

        @Factory
        public static Matcher<String> goodMethod() {
            return null;
        }

        @Factory
        public static String anotherGoodMethod() {
            return null;
        }

        @Factory
        public static void wrongReturnType() {
        }

    }

    @Test public void
    onlyReadsPublicStaticAnnotatedMethodsThatReturnNonVoid() {
        final ReflectiveFactoryReader reader = new ReflectiveFactoryReader(MatchersWithDodgySignatures.class);
        final List<FactoryMethod> methods = methodsReadBy(reader);

        assertEquals(2, methods.size());
        assertEquals("anotherGoodMethod", methods.get(0).getName());
        assertEquals("goodMethod", methods.get(1).getName());
    }

    public static class GenerifiedMatchers {

        @Factory
        public static Matcher<Comparator<String>> generifiedType() {
            return null;
        }

        @SuppressWarnings("rawtypes")
        @Factory
        public static Matcher noGenerifiedType() {
            return null;
        }

        @Factory
        public static Matcher<Map<? extends Set<Long>, Factory>> crazyType() {
            return null;
        }

    }

    @Test public void
    readsFullyQualifiedGenericType() {
        FactoryMethod method = readMethod(GenerifiedMatchers.class, "generifiedType");
        assertEquals("java.util.Comparator<java.lang.String>", method.getGenerifiedType());
    }

    @Test public void
    readsNullGenerifiedTypeIfNotPresent() {
        FactoryMethod method = readMethod(GenerifiedMatchers.class, "noGenerifiedType");
        assertNull(method.getGenerifiedType());
    }

    @Test public void
    readsGenericsInGenericType() {
        FactoryMethod method = readMethod(GenerifiedMatchers.class, "crazyType");
        assertEquals(
                "java.util.Map<? extends java.util.Set<java.lang.Long>, org.hamcrest.Factory>",
                method.getGenerifiedType());
    }

    public static class ParameterizedMatchers {

        @Factory
        public static Matcher<String> withParam(String someString, int[] numbers, Collection<Object> things) {
            return null;
        }

        @Factory
        public static Matcher<String> withArray(String[] array) {
            return null;
        }

        @Factory
        public static Matcher<String> withVarArgs(String... things) {
            return null;
        }

        @Factory
        public static Matcher<String> withGenerifiedParam(Collection<? extends Comparable<String>> things, Set<String[]>[] x) {
            return null;
        }
        
        @Factory
        public static Matcher<String> withInnerClass(Map.Entry<String, Integer> x) {
        	return null;
        }

    }
    
    @Test public void
    readsInnterClass() {
        FactoryMethod method = readMethod(ParameterizedMatchers.class, "withInnerClass");
        List<FactoryMethod.Parameter> params = method.getParameters();
        assertEquals(1, params.size());

        assertEquals("java.util.Map.Entry<java.lang.String, java.lang.Integer>", params.get(0).getType());
    }

    @Test public void
    readsParameterTypes() {
        FactoryMethod method = readMethod(ParameterizedMatchers.class, "withParam");
        List<FactoryMethod.Parameter> params = method.getParameters();
        assertEquals(3, params.size());

        assertEquals("java.lang.String", params.get(0).getType());
        assertEquals("int[]", params.get(1).getType());
        assertEquals("java.util.Collection<java.lang.Object>", params.get(2).getType());
    }

    @Test public void
    readsArrayAndVarArgParameterTypes() {
        FactoryMethod arrayMethod = readMethod(ParameterizedMatchers.class, "withArray");
        assertEquals("java.lang.String[]", arrayMethod.getParameters().get(0).getType());

        FactoryMethod varArgsMethod = readMethod(ParameterizedMatchers.class, "withVarArgs");
        assertEquals("java.lang.String...", varArgsMethod.getParameters().get(0).getType());
    }

    @Test public void
    readsGenerifiedParameterTypes() {
        FactoryMethod method = readMethod(ParameterizedMatchers.class, "withGenerifiedParam");
        
        assertEquals("java.util.Collection<? extends java.lang.Comparable<java.lang.String>>",
                     method.getParameters().get(0).getType());

        String expected = System.getProperty("java.version").startsWith("1.7.")
                ? "java.util.Set<[Ljava.lang.String;>[]"
                : "java.util.Set<java.lang.String[]>[]";

        assertEquals(expected, method.getParameters().get(1).getType());
    }

    @Test public void
    cannotReadParameterNamesSoMakesThemUpInstead() {
        FactoryMethod method = readMethod(ParameterizedMatchers.class, "withParam");
        List<FactoryMethod.Parameter> params = method.getParameters();

        assertEquals("param1", params.get(0).getName());
        assertEquals("param2", params.get(1).getName());
        assertEquals("param3", params.get(2).getName());
    }

    public static class ExceptionalMatchers {

        @Factory
        public static Matcher<String> withExceptions() throws Error, IOException, RuntimeException {
            return null;
        }

    }

    @Test public void
    readsExceptions() {
        FactoryMethod method = readMethod(ExceptionalMatchers.class, "withExceptions");
        List<String> exceptions = method.getExceptions();
        assertEquals(3, exceptions.size());

        assertEquals("java.lang.Error", exceptions.get(0));
        assertEquals("java.io.IOException", exceptions.get(1));
        assertEquals("java.lang.RuntimeException", exceptions.get(2));
    }

    public static class WithJavaDoc {

        /**
         * Look at me!
         *
         * @return something
         */
        @Factory
        public static Matcher<String> documented() {
            return null;
        }

    }

    @Test public void
    cannotReadJavaDoc() {
        // JavaDoc information is not available through reflection alone.
        FactoryMethod method = readMethod(WithJavaDoc.class, "documented");
        assertEquals(null, method.getJavaDoc());
    }

    public static class G {

        @Factory
        public static <T, V extends List<String> & Comparable<String>> Matcher<Map<T, V[]>> x(Set<T> t, V v) {
            return null;
        }

    }

    @Test public void
    readsGenericTypeParameters() {
        FactoryMethod method = readMethod(G.class, "x");
        assertEquals("T", method.getGenericTypeParameters().get(0));
        assertEquals("V extends java.util.List<java.lang.String> & java.lang.Comparable<java.lang.String>",
                method.getGenericTypeParameters().get(1));
        assertEquals("java.util.Map<T, V[]>", method.getGenerifiedType());
        assertEquals("java.util.Set<T>", method.getParameters().get(0).getType());
        assertEquals("V", method.getParameters().get(1).getType());
    }

    public static final class SubMatcher<T> implements Matcher<T> {
        @Override public void describeTo(Description description) { }
        @Override public boolean matches(Object item) { return false; }
        @Override public void describeMismatch(Object item, Description mismatchDescription) { }
        @Override @Deprecated public void _dont_implement_Matcher___instead_extend_BaseMatcher_() { }
    }

    public static class SubclassOfMatcher {
        @Factory public static SubMatcher<?> subclassMethod() { return null; }
    }

    @Test public void
    catchesSubclasses() {
        assertNotNull(readMethod(SubclassOfMatcher.class, "subclassMethod"));
    }

    private static List<FactoryMethod> methodsReadBy(final ReflectiveFactoryReader reader) {
        final List<FactoryMethod> extractedMethods = new ArrayList<FactoryMethod>();
        for (FactoryMethod factoryMethod : reader) {
            extractedMethods.add(factoryMethod);
        }
        Collections.sort(extractedMethods, new Comparator<FactoryMethod>() {
            @Override public int compare(FactoryMethod o1, FactoryMethod o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return extractedMethods;
    }

    private static FactoryMethod readMethod(Class<?> cls, String methodName) {
        for (FactoryMethod method : new ReflectiveFactoryReader(cls)) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}