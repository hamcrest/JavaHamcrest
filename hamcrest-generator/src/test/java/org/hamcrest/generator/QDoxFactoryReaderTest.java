package org.hamcrest.generator;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.ArrayList;
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
                "  Matcher someMethod(String realParamName) { ... } \n" +
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
                "  Matcher someMethod(java.util.Collection<String> realParamName) { ... } \n" +
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
                "  Matcher someMethod(java.lang.String... realParamName) { ... } \n" +
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
                "  Matcher someMethod() { ... } \n" +
                "}\n";
        FactoryMethod factoryMethod = wrapUsingQDoxedSource(method, "org.SomeClass", input);

        assertEquals("This class does something.\n\n@return stuff.\n",
                factoryMethod.getJavaDoc());
    }

    private static FactoryMethod wrapUsingQDoxedSource(FactoryMethod originalMethod, String className, String input) {
        List<FactoryMethod> originalMethods = new ArrayList<FactoryMethod>();
        originalMethods.add(originalMethod);

        QDox qdox = new QDox();
        qdox.addSource(new StringReader(input));

        QDoxFactoryReader qDoxFactoryReader = new QDoxFactoryReader(
                originalMethods, qdox, className);
        return getFirstFactoryMethod(qDoxFactoryReader);
    }

    private static FactoryMethod getFirstFactoryMethod(QDoxFactoryReader qDoxFactoryReader) {
        Iterator<FactoryMethod> iterator = qDoxFactoryReader.iterator();
        iterator.hasNext();
        return iterator.next();
    }
}
