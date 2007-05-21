package org.hamcrest.generator;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import junit.framework.TestCase;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QDoxFactoryReaderTest extends TestCase {

    public void testExtractsOriginalParameterNamesFromSource() {
        FactoryMethod method = new FactoryMethod("org.SomeClass", "someMethod");
        method.addParameter("java.lang.String", "badParamName");

        String input = "" +
                "package org;\n" +
                "class SomeClass {\n" +
                "  Matcher someMethod(String realParamName) { ... } \n" +
                "}\n";
        FactoryMethod factoryMethod = wrapUsingQDoxedSource(method, input);

        assertEquals("java.lang.String", factoryMethod.getParameters().get(0).getType());
        assertEquals("realParamName", factoryMethod.getParameters().get(0).getName());
    }

    public void testExtractsOriginalJavaDocFromSource() {
        FactoryMethod method = new FactoryMethod("org.SomeClass", "someMethod");

        String input = "" +
                "package org;\n" +
                "class SomeClass {\n" +
                "  /**\n" +
                "   * This class\n" +
                "   * does something.\n" +
                "   *\n" +
                "   * @return stuff.\n" +
                "   */\n" +
                "  Matcher someMethod() { ... } \n" +
                "}\n";
        FactoryMethod factoryMethod = wrapUsingQDoxedSource(method, input);

        assertEquals("This class does something.\n\n@return stuff.\n",
                factoryMethod.getJavaDoc());
    }

    private FactoryMethod wrapUsingQDoxedSource(FactoryMethod originalMethod, String input) {
        List<FactoryMethod> originalMethods = new ArrayList<FactoryMethod>();
        originalMethods.add(originalMethod);

        JavaClass javaClass = parseSource(input);

        QDoxFactoryReader qDoxFactoryReader = new QDoxFactoryReader(originalMethods, javaClass);
        return getFirstFactoryMethod(qDoxFactoryReader);
    }

    private JavaClass parseSource(String input) {
        JavaDocBuilder javaDocBuilder = new JavaDocBuilder();
        JavaSource javaSource = javaDocBuilder.addSource(new StringReader(input));
        return javaSource.getClasses()[0];
    }

    private FactoryMethod getFirstFactoryMethod(QDoxFactoryReader qDoxFactoryReader) {
        Iterator<FactoryMethod> iterator = qDoxFactoryReader.iterator();
        iterator.hasNext();
        return iterator.next();
    }


}
