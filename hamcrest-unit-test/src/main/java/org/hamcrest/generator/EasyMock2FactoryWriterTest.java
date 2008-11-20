package org.hamcrest.generator;

import junit.framework.TestCase;

import java.io.StringWriter;
import java.io.IOException;

public class EasyMock2FactoryWriterTest extends TestCase {

    private FactoryWriter factoryWriter;
    private StringWriter output = new StringWriter();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        factoryWriter = new EasyMock2FactoryWriter(output, "com.blah", "EasyMatchers");
    }

    public void testWritesMethodDelegationMethodWrappedInAdapter() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "anyObject", "unusedReturnType");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static java.lang.Object anyObject() {\n" +
                "    org.hamcrest.integration.EasyMockAdapter.adapt(\n" +
                "      com.example.MyMatcher.anyObject());\n" +
                "    return null;\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesReturnType() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "anyString", "unusedReturnType");
        method.setGenerifiedType("String");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static String anyString() {\n" +
                "    org.hamcrest.integration.EasyMockAdapter.adapt(\n" +
                "      com.example.MyMatcher.anyString());\n" +
                "    return null;\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesAdvancedGenerifiedMatcherType() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "weirdThing", "unusedReturnType");
        method.setGenerifiedType("java.util.Map<com.Foo<Cheese>,?>");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static java.util.Map<com.Foo<Cheese>,?> weirdThing() {\n" +
                "    org.hamcrest.integration.EasyMockAdapter.adapt(\n" +
                "      com.example.MyMatcher.weirdThing());\n" +
                "    return null;\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesParameters() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "between", "unusedReturnType");
        method.addParameter("int[]", "lower");
        method.addParameter("com.blah.Cheesable<Long>...", "upper");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static java.lang.Object between(int[] lower, com.blah.Cheesable<Long>... upper) {\n" +
                "    org.hamcrest.integration.EasyMockAdapter.adapt(\n" +
                "      com.example.MyMatcher.between(lower, upper));\n" +
                "    return null;\n" +
                "  }\n" +
                "\n",
                output.toString());
    }

    public void testWritesExceptions() throws IOException {
        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "tricky", "unusedReturnType");
        method.addException("java.io.IOException");
        method.addException("com.foo.CheeselessException");

        factoryWriter.writeMethod(method.getName(), method);
        assertEquals("" +
                "  public static java.lang.Object tricky() throws java.io.IOException, com.foo.CheeselessException {\n" +
                "    org.hamcrest.integration.EasyMockAdapter.adapt(\n" +
                "      com.example.MyMatcher.tricky());\n" +
                "    return null;\n" +
                "  }\n" +
                "\n",
                output.toString());
    }
//
//    public void testWritesGenericTypeParameters() throws IOException {
//        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "tricky");
//        method.addGenericTypeParameter("T");
//        method.addGenericTypeParameter("V extends String & Cheese");
//        method.addParameter("T", "t");
//        method.addParameter("List<V>", "v");
//
//        factoryWriter.writeMethod(method.getName(), method);
//        assertEquals("" +
//                "  public static <T, V extends String & Cheese> org.hamcrest.Matcher tricky(T t, List<V> v) {\n" +
//                "    return com.example.MyMatcher.tricky(t, v);\n" +
//                "  }\n" +
//                "\n",
//                output.toString());
//    }
//
//    public void testWritesJavaDoc() throws IOException {
//        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "needsDoc");
//        method.setJavaDoc("This is a complicated method.\nIt needs docs.\n\n@see MoreStuff");
//
//        factoryWriter.writeMethod(method.getName(), method);
//        assertEquals("" +
//                "  /**\n" +
//                "   * This is a complicated method.\n" +
//                "   * It needs docs.\n" +
//                "   * \n" +
//                "   * @see MoreStuff\n" +
//                "   */\n" +
//                "  public static org.hamcrest.Matcher needsDoc() {\n" +
//                "    return com.example.MyMatcher.needsDoc();\n" +
//                "  }\n" +
//                "\n",
//                output.toString());
//    }
//
//    public void testWritesMethodWithNameOverriden() throws IOException {
//        FactoryMethod method = new FactoryMethod("com.example.MyMatcher", "eq");
//
//        factoryWriter.writeMethod("anotherName", method);
//        assertEquals("" +
//                "  public static org.hamcrest.Matcher anotherName() {\n" +
//                "    return com.example.MyMatcher.eq();\n" +
//                "  }\n" +
//                "\n",
//                output.toString());
//    }

    // primitives
    // arrays?
    // and/or/not/etc


}
