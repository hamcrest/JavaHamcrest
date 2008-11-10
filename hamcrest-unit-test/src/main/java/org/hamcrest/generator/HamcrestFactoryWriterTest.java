package org.hamcrest.generator;

import java.io.IOException;
import java.io.StringWriter;

import junit.framework.TestCase;

public class HamcrestFactoryWriterTest extends TestCase {
    private FactoryWriter factoryWriter;
    private StringWriter output = new StringWriter();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        factoryWriter = new HamcrestFactoryWriter(null, null, output);
    }

    public void testWritesMethodDelegationMethod() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "anyObject", "matcher.ReturnType");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static matcher.ReturnType anyObject() {\n" +
                "    return com.example.MyMatcher.anyObject();\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesCompleteJavaSource() throws IOException {
        factoryWriter = new HamcrestFactoryWriter("com.some.package", "SomeClass", output);
        factoryWriter.writeHeader();
        factoryWriter.writeMethod("method1", new FactoryMethod("com.example.MyMatcher", "method1", "matcher.ReturnType"));
        factoryWriter.writeMethod("method2", new FactoryMethod("com.example.MyMatcher", "method2", "matcher.ReturnType"));
        factoryWriter.writeFooter();

        assertEquals("" +
                "// Generated source.\n" +
                "package com.some.package;\n" +
                "\n" +
                "public class SomeClass {\n" +
                "\n" +
                "  public static matcher.ReturnType method1() {\n" +
                "    return com.example.MyMatcher.method1();\n" +
                "  }\n" +
                "\n" +
                "  public static matcher.ReturnType method2() {\n" +
                "    return com.example.MyMatcher.method2();\n" +
                "  }\n" +
                "\n" +
                "}\n",
                output.toString());
    }

    public void testWritesGenerifiedMatcherType() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "anyString", "matcher.ReturnType");
        method.setGenerifiedType("String");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static matcher.ReturnType<String> anyString() {\n" +
                "    return com.example.MyMatcher.anyString();\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesAdvancedGenerifiedMatcherType() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "weirdThing", "matcher.ReturnType");
        method.setGenerifiedType("java.util.Map<com.Foo<Cheese>,?>");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static matcher.ReturnType<java.util.Map<com.Foo<Cheese>,?>> weirdThing() {\n" +
                "    return com.example.MyMatcher.weirdThing();\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesParameters() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "between", "matcher.ReturnType");
        method.addParameter("int[]", "lower");
        method.addParameter("com.blah.Cheesable<Long>...", "upper");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static matcher.ReturnType between(int[] lower, com.blah.Cheesable<Long>... upper) {\n" +
                "    return com.example.MyMatcher.between(lower, upper);\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesExceptions() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "tricky", "matcher.ReturnType");
        method.addException("java.io.IOException");
        method.addException("com.foo.CheeselessException");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static matcher.ReturnType tricky() throws java.io.IOException, com.foo.CheeselessException {\n" +
                "    return com.example.MyMatcher.tricky();\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesGenericTypeParameters() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "tricky", "matcher.ReturnType");
        method.addGenericTypeParameter("T");
        method.addGenericTypeParameter("V extends String & Cheese");
        method.addParameter("T", "t");
        method.addParameter("List<V>", "v");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static <T, V extends String & Cheese> matcher.ReturnType tricky(T t, List<V> v) {\n" +
                "    return com.example.MyMatcher.<T,V>tricky(t, v);\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesJavaDoc() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "needsDoc", "matcher.ReturnType");
        method.setJavaDoc("This is a complicated method.\nIt needs docs.\n\n@see MoreStuff");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  /**\n" +
                "   * This is a complicated method.\n" +
                "   * It needs docs.\n" +
                "   * \n" +
                "   * @see MoreStuff\n" +
                "   */\n" +
                "  public static matcher.ReturnType needsDoc() {\n" +
                "    return com.example.MyMatcher.needsDoc();\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesMethodWithNameOverriden() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "eq", "matcher.ReturnType");

        factoryWriter.writeMethod("anotherName", method);
        assertEquals("" +
                "  public static matcher.ReturnType anotherName() {\n" +
                "    return com.example.MyMatcher.eq();\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

}
