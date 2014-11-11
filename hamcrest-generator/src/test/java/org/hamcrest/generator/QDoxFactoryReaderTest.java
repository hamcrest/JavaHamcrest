package org.hamcrest.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public final class QDoxFactoryReaderTest {

    @Test public void
    extractsOriginalParameterNamesFromSource() {
        FactoryMethod method = new FactoryMethod("org.SomeClass", "someMethod", "unusedReturnType");
        method.addParameter("java.lang.String", "badParamName");

        String input = "" +
                "package org;\n" +
                "class SomeClass {\n" +
                "  @org.hamcrest.Factory" +
                "  public static Matcher someMethod(String realParamName) { ... } \n" +
                "}\n";
        FactoryMethod factoryMethod = wrapUsingQDoxedSource(method, "org.SomeClass", input);

        assertEquals("java.lang.String", factoryMethod.getParameters().get(0).getType());
        assertEquals("realParamName", factoryMethod.getParameters().get(0).getName());
    }

    @Test public void
    extractsOriginalGenericParameterNamesFromSource() {
        FactoryMethod method = new FactoryMethod("org.SomeClass", "someMethod", "unusedReturnType");
        method.addParameter("java.util.Collection<java.lang.String>", "badParamName");

        String input = "" +
                "package org;\n" +
                "class SomeClass {\n" +
                "  @org.hamcrest.Factory" +
                "  public static Matcher someMethod(java.util.Collection<String> realParamName) { ... } \n" +
                "}\n";
        FactoryMethod factoryMethod = wrapUsingQDoxedSource(method, "org.SomeClass", input);

        assertEquals("java.util.Collection<java.lang.String>", factoryMethod.getParameters().get(0).getType());
        assertEquals("realParamName", factoryMethod.getParameters().get(0).getName());
    }

    @Test public void
    extractsOriginalVarArgParameterNamesFromSource() {
        FactoryMethod method = new FactoryMethod("org.SomeClass", "someMethod", "unusedReturnType");
        method.addParameter("java.lang.String...", "badParamName");

        String input = "" +
                "package org;\n" +
                "class SomeClass {\n" +
                "  @org.hamcrest.Factory" +
                "  public static Matcher someMethod(java.lang.String... realParamName) { ... } \n" +
                "}\n";
        FactoryMethod factoryMethod = wrapUsingQDoxedSource(method, "org.SomeClass", input);

        assertEquals("java.lang.String...", factoryMethod.getParameters().get(0).getType());
        assertEquals("realParamName", factoryMethod.getParameters().get(0).getName());
    }

    @Test public void
    extractsOriginalJavaDocFromSource() {
        FactoryMethod method = new FactoryMethod("org.SomeClass", "someMethod", "unusedReturnType");

        String input = "" +
                "package org;\n" +
                "class SomeClass {\n" +
                "  /**\n" +
                "   * This class does something.\n" +
                "   *\n" +
                "   * @return stuff.\n" +
                "   */\n" +
                "  @org.hamcrest.Factory" +
                "  public static Matcher someMethod() { ... } \n" +
                "}\n";
        FactoryMethod factoryMethod = wrapUsingQDoxedSource(method, "org.SomeClass", input);

        assertEquals("This class does something.\n\n@return stuff.\n",
                factoryMethod.getJavaDoc());
    }

    @Test public void
    iteratesOverFactoryMethods() throws FileNotFoundException {
        final QDoxFactoryReader reader = readerForTestClass("SimpleSetOfMatchers");
        final List<FactoryMethod> methods = methodsReadBy(reader);

        assertEquals(2, methods.size());

        final String expectedClass = "test.SimpleSetOfMatchers";

        assertEquals("firstMethod", methods.get(0).getName());
        assertEquals(expectedClass, methods.get(0).getMatcherClass());

        assertEquals("secondMethod", methods.get(1).getName());
        assertEquals(expectedClass, methods.get(1).getMatcherClass());
    }

    @Test public void
    onlyReadsPublicStaticAnnotatedMethodsThatReturnNonVoid() throws FileNotFoundException {
        final QDoxFactoryReader reader = readerForTestClass("MatchersWithDodgySignatures");
        final List<FactoryMethod> methods = methodsReadBy(reader);

        assertEquals(2, methods.size());
        assertEquals("anotherGoodMethod", methods.get(0).getName());
        assertEquals("goodMethod", methods.get(1).getName());
    }

    @Test public void
    readsFullyQualifiedGenericType() throws FileNotFoundException {
        FactoryMethod method = readMethod("GenerifiedMatchers", "generifiedType");
        assertEquals("org.hamcrest.Matcher<java.util.Comparator<java.lang.String>>", method.getReturnType());
    }

    @Test public void
    readsNullGenerifiedTypeIfNotPresent() throws FileNotFoundException {
        FactoryMethod method = readMethod("GenerifiedMatchers", "noGenerifiedType");
        assertEquals("org.hamcrest.Matcher", method.getReturnType());
    }

    @Test public void
    readsGenericsInGenericType() throws FileNotFoundException {
        FactoryMethod method = readMethod("GenerifiedMatchers", "crazyType");
        assertEquals(
                "org.hamcrest.Matcher<java.util.Map<? extends java.util.Set<java.lang.Long>,org.hamcrest.Factory>>",
                method.getReturnType());
    }

    @Test public void
    readsParameterTypes() throws FileNotFoundException {
        FactoryMethod method = readMethod("ParameterizedMatchers", "withParam");
        List<FactoryMethod.Parameter> params = method.getParameters();
        assertEquals(3, params.size());

        assertEquals("java.lang.String", params.get(0).getType());
        assertEquals("int[]", params.get(1).getType());
        assertEquals("java.util.Collection<java.lang.Object>", params.get(2).getType());
    }

    @Test public void
    readsArrayAndVarArgParameterTypes() throws FileNotFoundException {
        FactoryMethod arrayMethod = readMethod("ParameterizedMatchers", "withArray");
        assertEquals("java.lang.String[]", arrayMethod.getParameters().get(0).getType());

        FactoryMethod varArgsMethod = readMethod("ParameterizedMatchers", "withVarArgs");
        assertEquals("java.lang.String...", varArgsMethod.getParameters().get(0).getType());
    }

    @Test public void
    readsGenerifiedParameterTypes() throws FileNotFoundException {
        FactoryMethod method = readMethod("ParameterizedMatchers", "withGenerifiedParam");

        assertEquals("java.util.Collection<? extends java.lang.Comparable<java.lang.String>>",
                     method.getParameters().get(0).getType());

        String expected = "java.util.Set<java.lang.String[]>[]";

        assertEquals(expected, method.getParameters().get(1).getType());
    }

    @Test public void
    canReadParameterNames() throws FileNotFoundException {
        FactoryMethod method = readMethod("ParameterizedMatchers", "withParam");
        List<FactoryMethod.Parameter> params = method.getParameters();

        assertEquals("someString", params.get(0).getName());
        assertEquals("numbers", params.get(1).getName());
        assertEquals("things", params.get(2).getName());
    }

    @Test public void
    readsExceptions() throws FileNotFoundException {
        FactoryMethod method = readMethod("ExceptionalMatchers", "withExceptions");
        List<String> exceptions = method.getExceptions();
        assertEquals(3, exceptions.size());

        assertEquals("java.lang.Error", exceptions.get(0));
        assertEquals("java.io.IOException", exceptions.get(1));
        assertEquals("java.lang.RuntimeException", exceptions.get(2));
    }

    @Test public void
    canReadJavaDoc() throws FileNotFoundException {
        FactoryMethod method = readMethod("WithJavaDoc", "documented");
        assertEquals("Look at me!\n\n@return something\n", method.getJavaDoc());
    }

    @Test public void
    readsGenericTypeParameters() throws FileNotFoundException {
        FactoryMethod method = readMethod("G", "x");
        assertEquals("T", method.getGenericTypeParameters().get(0));
        assertEquals("V extends java.util.List<java.lang.String> & java.lang.Comparable<java.lang.String>",
                method.getGenericTypeParameters().get(1));
        assertEquals("org.hamcrest.Matcher<java.util.Map<T,V[]>>", method.getReturnType());
        assertEquals("java.util.Set<T>", method.getParameters().get(0).getType());
        assertEquals("V", method.getParameters().get(1).getType());
    }

    @Test public void
    catchesSubclasses() throws FileNotFoundException {
        assertNotNull(readMethod("SubclassOfMatcher", "subclassMethod"));
    }

    @Test public void
    usesCorrectNameForNestedClasses() throws FileNotFoundException {
        FactoryMethod method = readMethod("MatcherWithNestedClass", "firstMethod");

        assertEquals("test.MatcherWithNestedClass.SpecificMatcher", method.getReturnType());
    }

    private static List<FactoryMethod> methodsReadBy(final Iterable<FactoryMethod> reader) {
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

    private static QDoxFactoryReader readerForTestClass(String name)
            throws FileNotFoundException {
        QDox qdox = new QDox();
        qdox.addSource(new FileReader("src/test/java-source/test/" + name + ".java"));

        return new QDoxFactoryReader(qdox, "test." + name);
    }

    private static FactoryMethod readMethod(String name, String methodName) throws FileNotFoundException {
        for (FactoryMethod method : readerForTestClass(name)) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private static FactoryMethod wrapUsingQDoxedSource(FactoryMethod originalMethod, String className, String input) {
        List<FactoryMethod> originalMethods = new ArrayList<FactoryMethod>();
        originalMethods.add(originalMethod);

        QDox qdox = new QDox();
        qdox.addSource(new StringReader(input));

        QDoxFactoryReader qDoxFactoryReader = new QDoxFactoryReader(qdox, className);
        return getFirstFactoryMethod(qDoxFactoryReader);
    }

    private static FactoryMethod getFirstFactoryMethod(QDoxFactoryReader qDoxFactoryReader) {
        Iterator<FactoryMethod> iterator = qDoxFactoryReader.iterator();
        assertTrue(iterator.hasNext());
        return iterator.next();
    }
}
