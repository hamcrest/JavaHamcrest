package org.hamcrest.generator;

import junit.framework.TestCase;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QDoxFactoryReaderTest extends TestCase {

    public void testExtractsOriginalParameterNamesFromSource() {
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
    
    public void testExtractsOriginalGenericParameterNamesFromSource() {
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
    
    public void testExtractsOriginalVarArgParameterNamesFromSource() {
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

    public void testExtractsOriginalJavaDocFromSource() {
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

    private static FactoryMethod wrapUsingQDoxedSource(FactoryMethod originalMethod,
                                                String className, String input) {
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
