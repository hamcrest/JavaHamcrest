package org.hamcrest.generator;

import junit.framework.TestCase;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class ReflectiveFactoryReaderTest extends TestCase {

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

    public void testIteratesOverFactoryMethods() {
        Iterable<FactoryMethod> reader = new ReflectiveFactoryReader(SimpleSetOfMatchers.class);
        Iterator<FactoryMethod> methods = reader.iterator();

        assertTrue("Expected first method", methods.hasNext());
        FactoryMethod firstMethod = methods.next();
        assertEquals("firstMethod", firstMethod.getName());
        assertEquals(SimpleSetOfMatchers.class.getName().replace('$', '.'), firstMethod.getMatcherClass());

        assertTrue("Expected second method", methods.hasNext());
        FactoryMethod secondMethod = methods.next();
        assertEquals("secondMethod", secondMethod.getName());
        assertEquals(SimpleSetOfMatchers.class.getName().replace('$', '.'), secondMethod.getMatcherClass());

        assertFalse("Expected no more methods", methods.hasNext());
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

    public void testOnlyReadsPublicStaticAnnotatedMethodsThatReturnNonVoid() {
        Iterable<FactoryMethod> reader = new ReflectiveFactoryReader(MatchersWithDodgySignatures.class);
        Iterator<FactoryMethod> methods = reader.iterator();

        assertTrue("Expected first method", methods.hasNext());
        assertEquals("goodMethod", methods.next().getName());

        assertTrue("Expected second method", methods.hasNext());
        assertEquals("anotherGoodMethod", methods.next().getName());

        assertFalse("Expected no more methods", methods.hasNext());
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

    public void testReadsFullyQualifiedGenericType() {
        FactoryMethod method = readMethod(GenerifiedMatchers.class, "generifiedType");
        assertEquals("java.util.Comparator<java.lang.String>", method.getGenerifiedType());
    }

    public void testReadsNullGenerifiedTypeIfNotPresent() {
        FactoryMethod method = readMethod(GenerifiedMatchers.class, "noGenerifiedType");
        assertNull(method.getGenerifiedType());
    }

    public void testReadsGenericsInGenericType() {
        FactoryMethod method = readMethod(GenerifiedMatchers.class, "crazyType");
        assertEquals(
                "java.util.Map<? extends java.util.Set<java.lang.Long>, org.hamcrest.Factory>",
                method.getGenerifiedType());
    }

    public static class ParamterizedMatchers {

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

    }

    public void testReadsParameterTypes() {
        FactoryMethod method = readMethod(ParamterizedMatchers.class, "withParam");
        List<FactoryMethod.Parameter> params = method.getParameters();
        assertEquals(3, params.size());

        assertEquals("java.lang.String", params.get(0).getType());
        assertEquals("int[]", params.get(1).getType());
        assertEquals("java.util.Collection<java.lang.Object>", params.get(2).getType());
    }

    public void testReadsArrayAndVarArgParameterTypes() {
        FactoryMethod arrayMethod = readMethod(ParamterizedMatchers.class, "withArray");
        assertEquals("java.lang.String[]", arrayMethod.getParameters().get(0).getType());

        FactoryMethod varArgsMethod = readMethod(ParamterizedMatchers.class, "withVarArgs");
        assertEquals("java.lang.String...", varArgsMethod.getParameters().get(0).getType());
    }

    public void testReadsGenerifiedParameterTypes() {
        FactoryMethod method = readMethod(ParamterizedMatchers.class, "withGenerifiedParam");
        assertEquals("java.util.Collection<? extends java.lang.Comparable<java.lang.String>>",
                method.getParameters().get(0).getType());
        assertEquals("java.util.Set<java.lang.String[]>[]",
                method.getParameters().get(1).getType());
    }

    public void testCannotReadParameterNamesSoMakesThemUpInstead() {
        FactoryMethod method = readMethod(ParamterizedMatchers.class, "withParam");
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

    public void testReadsExceptions() {
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

    public void testCannotReadJavaDoc() {
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

    public void testReadsGenericTypeParameters() {
        FactoryMethod method = readMethod(G.class, "x");
        assertEquals("T", method.getGenericTypeParameters().get(0));
        assertEquals("V extends java.util.List<java.lang.String> & java.lang.Comparable<java.lang.String>",
                method.getGenericTypeParameters().get(1));
        assertEquals("java.util.Map<T, V[]>", method.getGenerifiedType());
        assertEquals("java.util.Set<T>", method.getParameters().get(0).getType());
        assertEquals("V", method.getParameters().get(1).getType());
    }

    public static class SubclassOfMatcher {
        @Factory
        public static BaseMatcher<?> subclassMethod() {
            return null;
        }
    }

    public void testCatchesSubclasses() {
        assertNotNull(readMethod(SubclassOfMatcher.class, "subclassMethod"));
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